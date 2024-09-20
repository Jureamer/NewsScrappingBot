package com.example.mknewsscrappingbot.domain;

import com.example.mknewsscrappingbot.data.Article;
import com.example.mknewsscrappingbot.data.keywordMapping.IKeywordMapping;
import com.example.mknewsscrappingbot.data.keywordMapping.KeywordMappingFactory;
import com.example.mknewsscrappingbot.data.newsSource.NewsSourceFactory;
import net.dv8tion.jda.api.EmbedBuilder;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsCrawlingService {
    private final ArrayList<String> newsNames = new ArrayList<>(Arrays.asList("HK", "MK", "CS", "JA", "DA"));
    private final ArticleScraperFactory articleScraperFactory;
    private final NewsSourceFactory newsSourceFactory;
    private final KeywordMappingFactory keywordMappingFactory;
    private final WebDriver driver;
    private final ArticleRepository articleRepository;
    private final ChatService chatService;

    public NewsCrawlingService(
            ArticleScraperFactory articleScraperFactory,
            NewsSourceFactory newsSourceFactory,
            KeywordMappingFactory keywordMappingFactory,
            SeleniumDriver seleniumDriver,
            ArticleRepository articleRepository,
            ChatService chatService
    ) {
        this.articleScraperFactory = articleScraperFactory;
        this.newsSourceFactory = newsSourceFactory;
        this.keywordMappingFactory = keywordMappingFactory;
        this.driver = seleniumDriver.getDriver();
        this.articleRepository = articleRepository;
        this.chatService = chatService;
    }

    public void execute() {
        for (String media : newsNames) {
            ArticleScraper articleScraper = articleScraperFactory.getArticleScraper(media);
            IKeywordMapping keywordMapping = keywordMappingFactory.getKeywordMapping(media);
            String[] categories = keywordMapping.getKeywordValues();

            for (String category : categories) {
                List<String> urls = articleScraper.getTopUrlsByCategory(driver, category);

                System.out.println("Crawling " + media + " " + category + " articles....");


                int rank = 1;
                for (String url : urls) {
                    try {
                        driver.get(url);
                        articleScraper.waitForPageLoad(driver);

                        String title = articleScraper.extractTitle(driver);
                        String content = articleScraper.extractContent(driver);

                        System.out.println("Title: " + title);
                        System.out.println("Content: " + content);

                        String three_line_summary = chatService.getSummary(content);
                        System.out.println("*******요약된 내용입니다.********");
                        System.out.println(three_line_summary);


                        articleRepository.save(
                                new Article.ArticleBuilder()
                                        .media(media)
                                        .category(category)
                                        .rank(rank)
                                        .title(title)
                                        .content(three_line_summary)
                                        .link(url)
                                        .build());

                        rank++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
