package com.example.mknewsscrappingbot.domain;

import com.example.mknewsscrappingbot.data.Article;
import com.example.mknewsscrappingbot.data.keywordMapping.IKeywordMapping;
import com.example.mknewsscrappingbot.data.keywordMapping.KeywordMappingFactory;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class NewsCrawlingService {

    private final ArrayList<String> medias = new ArrayList<>(Arrays.asList("HK","MK","CS","JA","DA"));
    private static final Logger logger = LoggerFactory.getLogger(NewsCrawlingService.class);
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

    @Scheduled(fixedDelayString = "${crawling.interval}")
    public void executeCrawling() {
        logger.info("뉴스 크롤링 작업 시작...");

        for (String media : medias) {
            ArticleScraper articleScraper = articleScraperFactory.getArticleScraper(media);
            IKeywordMapping keywordMapping = keywordMappingFactory.getKeywordMapping(media);


            String[] categories = keywordMapping.getKeywordValues();

            logger.info("미디어: {}에 대한 크롤링을 시작합니다. 카테고리 : {}", media, categories);


            for (String category : categories) {
                logger.info("카테고리: {}에 대한 크롤링을 시작합니다.", category);
                List<String> urls = articleScraper.getTopUrlsByCategory(driver, category);

                System.out.println("Crawling " + media + " " + category + " articles....");

                int rank = 1;
                for (String url : urls) {
                    try {
                        logger.debug("URL로 이동 중: {}", url);
                        driver.get(url);
                        articleScraper.waitForPageLoad(driver);

                        String title = articleScraper.extractTitle(driver);
                        String content = articleScraper.extractContent(driver);
                        String summary = chatService.getSummary(content);
                        logger.debug("기사 제목: {}, 기사 요약: {}", title, summary);

                        articleRepository.save(
                                new Article.ArticleBuilder()
                                        .media(media)
                                        .category(category)
                                        .rank(rank)
                                        .title(title)
                                        .content(summary)
                                        .link(url)
                                        .build());
                        rank++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        logger.info("뉴스 크롤링 작업이 완료되었습니다.");
    }
}
