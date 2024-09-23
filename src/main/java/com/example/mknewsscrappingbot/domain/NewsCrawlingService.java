package com.example.mknewsscrappingbot.domain;

import com.example.mknewsscrappingbot.data.keywordMapping.IKeywordMapping;
import com.example.mknewsscrappingbot.data.keywordMapping.KeywordMappingFactory;
import com.example.mknewsscrappingbot.data.newsSource.NewsSource;
import com.example.mknewsscrappingbot.data.newsSource.NewsSourceFactory;
import org.openqa.selenium.WebDriver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class NewsCrawlingService {

    private final ArticleScraperFactory articleScraperFactory;
    private final ArrayList<String> newsNames = new ArrayList<>(Arrays.asList("HK","MK","CS","JA","DA"));
    private final NewsSourceFactory newsSourceFactory;
    private final KeywordMappingFactory keywordMappingFactory;
    private final WebDriver driver;
    public NewsCrawlingService(
            ArticleScraperFactory articleScraperFactory,
            NewsSourceFactory newsSourceFactory,
            KeywordMappingFactory keywordMappingFactory,
            WebDriver SeleniumDriver
    ) {
        this.articleScraperFactory = articleScraperFactory;
        this.newsSourceFactory = newsSourceFactory;
        this.keywordMappingFactory = keywordMappingFactory;
        this.driver = SeleniumDriver;
    }

    @Scheduled(fixedRateString = "${crawling.interval}")
    public void executeCrawling() {
        System.out.println("2시간마다 뉴스 크롤링 중...");

        for (String newsName : newsNames) {
            ArticleScraper articleScraper = articleScraperFactory.getArticleScraper(newsName);
            NewsSource newsSource = this.newsSourceFactory.getNewsSource(newsName);
            IKeywordMapping keywordMapping = this.keywordMappingFactory.getKeywordMapping(newsName);
        }

        articleScraperFactory.createScraper("hk").scrape();
    }


}
