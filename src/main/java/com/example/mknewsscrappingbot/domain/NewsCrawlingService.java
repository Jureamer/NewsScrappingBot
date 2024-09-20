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
    private WebDriver webDriver;

    public NewsCrawlingService(
            ArticleScraperFactory articleScraperFactory,
            NewsSourceFactory newsSourceFactory,
            KeywordMappingFactory keywordMappingFactory,
            SeleniumDriver seleniumDriver
    ) {
        this.articleScraperFactory = articleScraperFactory;
        this.newsSourceFactory = newsSourceFactory;
        this.keywordMappingFactory = keywordMappingFactory;
        this.webDriver = seleniumDriver.getWebDriver();
    }

    public void execute() {
        for (String newsName : newsNames) {
        ArticleScraper articleScraper = articleScraperFactory.getArticleScraper(newsName);
        IKeywordMapping keywordMapping = keywordMappingFactory.getKeywordMapping(newsName);


        List<String> categories = keywordMappingFactory.getKeywordMapping(newsName).getKeywords();
        List<String> urls = articleScraper.getTopUrlsByCategory(driver, category);

            System.out.println("Crawling " + media + " " + category + " articles....");

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

                    EmbedBuilder message = createEmbedMessage(rank, title, three_line_summary, url);
                    returnMessageArray.add(message);
                    rank++;

                    if (rank > 10) {
                        break;
                    }
    }


}
