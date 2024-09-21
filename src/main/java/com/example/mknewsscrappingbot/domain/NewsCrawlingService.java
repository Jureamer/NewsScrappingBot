package com.example.mknewsscrappingbot.domain;

import com.example.mknewsscrappingbot.data.Article;
import com.example.mknewsscrappingbot.data.keywordMapping.IKeywordMapping;
import com.example.mknewsscrappingbot.data.keywordMapping.KeywordMappingFactory;
import com.example.mknewsscrappingbot.data.newsSource.NewsSourceFactory;
import net.dv8tion.jda.api.EmbedBuilder;
import org.openqa.selenium.WebDriver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
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

    @Scheduled(fixedDelayString = "${crawling.interval}")
    public void execute() {
        System.out.println("뉴스 크롤링 작업 시작...");
//        for (String media : newsNames) {
//            processMedia(media);
//        }
        processMedia("CS");
        System.out.println("뉴스 크롤링 작업 종료...");
    }

    private void processMedia(String media) {
        ArticleScraper articleScraper = articleScraperFactory.getArticleScraper(media);
        IKeywordMapping keywordMapping = keywordMappingFactory.getKeywordMapping(media);
        Map.Entry<String, String>[] entryArray = keywordMapping.getEntryArray();

        for (Map.Entry<String, String> entry : entryArray) {
            String krCategory = entry.getKey();
            String enCategory = entry.getValue();
            List<String> urls = articleScraper.getTopUrlsByCategory(driver, enCategory);
            crawlArticlesByCategory(media, krCategory, enCategory, urls, articleScraper);
        }
    }

    private void crawlArticlesByCategory(String media, String krCategory, String enCategory, List<String> urls, ArticleScraper articleScraper) {
        System.out.println("Crawling " + media + " " + krCategory + " " + enCategory + " articles...");

        int rank = 1;
        for (String url : urls) {
            if (rank > 10) break;  // Stop after top 10 articles
            try {
                processArticle(media, krCategory, rank, url, articleScraper);
                rank++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void processArticle(String media, String krCategory, int rank, String url, ArticleScraper articleScraper) {
        driver.get(url);
        articleScraper.waitForPageLoad(driver);

        String title = articleScraper.extractTitle(driver);
        String content = articleScraper.extractContent(driver);
        String threeLineSummary = chatService.getSummary(content);

        System.out.println("Title: " + title);
        System.out.println("Content: " + content);
        System.out.println("******* 요약된 내용입니다. ********");
        System.out.println(threeLineSummary);

        saveArticle(media, krCategory, rank, title, threeLineSummary, url);
    }

    private void saveArticle(String media, String category, int rank, String title, String content, String url) {
        articleRepository.save(
                new Article.ArticleBuilder()
                        .media(media)
                        .category(category)
                        .rank(rank)
                        .title(title)
                        .content(content)
                        .link(url)
                        .build()
        );
    }
}
