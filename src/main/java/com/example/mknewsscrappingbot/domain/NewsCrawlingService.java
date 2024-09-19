package com.example.mknewsscrappingbot.domain;

import com.example.mknewsscrappingbot.data.Article;
import com.example.mknewsscrappingbot.data.keywordMapping.IKeywordMapping;
import com.example.mknewsscrappingbot.data.keywordMapping.KeywordMappingFactory;
import org.openqa.selenium.WebDriver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class NewsCrawlingService {

    private final ArrayList<String> medias = new ArrayList<>(Arrays.asList("HK","MK","CS","JA","DA"));
    private final ArticleScraperFactory articleScraperFactory;
    private final KeywordMappingFactory keywordMappingFactory;
    private final ChatService chatService;
    private final ArticleRepository articleRepository;
    private final WebDriver driver;

    public NewsCrawlingService(
            ArticleScraperFactory articleScraperFactory,
            KeywordMappingFactory keywordMappingFactory,
            ChatService chatService,
            ArticleRepository articleRepository,
            SeleniumDriver seleniumDriver
    ) {
        this.articleScraperFactory = articleScraperFactory;
        this.keywordMappingFactory = keywordMappingFactory;
        this.chatService = chatService;
        this.articleRepository = articleRepository;
        this.driver = seleniumDriver.getDriver();
    }

    @Scheduled(fixedRateString = "${crawling.interval}")
    public void executeCrawling() {
        System.out.println("2시간마다 뉴스 크롤링 중...");

        for (String media : medias) {
            ArticleScraper articleScraper = articleScraperFactory.getArticleScraper(media);
            IKeywordMapping keywordMapping = this.keywordMappingFactory.getKeywordMapping(media);


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
                        String three_line_summary = chatService.getSummary(content);


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
