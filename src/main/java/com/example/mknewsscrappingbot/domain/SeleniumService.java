package com.example.mknewsscrappingbot.domain;

import com.example.mknewsscrappingbot.data.Article;
import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeleniumService {

    private final WebDriver driver;
    private final ArticleRepository articleRepository;
    private final MkArticleScraper mkArticleScraper;
    private final CsArticleScraper csArticleScraper;
    private ArticleScraper articleScraper;

    public SeleniumService(
            SeleniumDriver seleniumDriver,
            ArticleRepository articleRepository,
            @Qualifier("mkArticleScraper") MkArticleScraper mkArticleScraper,
            @Qualifier("csArticleScraper") CsArticleScraper csArticleScraper,
            @Qualifier("mkArticleScraper") ArticleScraper articleScraper) {
        this.driver = seleniumDriver.getDriver();
        this.articleRepository = articleRepository;
        this.mkArticleScraper = mkArticleScraper;
        this.csArticleScraper = csArticleScraper;
        this.articleScraper =  articleScraper;
    }

    public ArrayList<String> crawling(String media, String category) {
        if (media.equals("MK")) {
            articleScraper = mkArticleScraper;
        } else {
            articleScraper = csArticleScraper;
        }

        ArrayList<String> returnMessageArray = new ArrayList<>();
        StringBuilder returnMessage = new StringBuilder();

        int rank = 1;

        List<Article> existingArticles = articleRepository.findByCategoryOrderByRank(category);

        for (Article article : existingArticles) {
            returnMessage.append(createMessage(rank, article.getTitle(), article.getContent(), article.getUrl()));
            rank++;

            // Split messages into chunks of 5
            if (rank == 6 || rank == 11) {
                returnMessageArray.add(returnMessage.toString());
                returnMessage = new StringBuilder();
            }
        }

        if (rank <= 10) {
            List<String> urls = articleScraper.getTopUrlsByCategory(driver, category);

            System.out.println("Crawling " + media + " " + category + " articles...");

            for (String url : urls) {
                try {
                    driver.get(url);
                    articleScraper.waitForPageLoad(driver);
                    String title = articleScraper.extractTitle(driver);
                    String content = articleScraper.extractContent(driver);
                    articleRepository.save(
                            new Article.ArticleBuilder()
                                    .media(media)
                                    .category(category)
                                    .rank(rank)
                                    .title(title)
                                    .content(content)
                                    .link(url)
                                    .build());
                    returnMessage.append(createMessage(rank, title, content, url));

                    rank++;

                    // Split messages into chunks of 5
                    if (rank == 6 || rank == 11) {
                        returnMessageArray.add(returnMessage.toString());
                        returnMessage = new StringBuilder();
                    }

                    // Stop if we have 10 articles
                    if (rank > 10) {
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return returnMessageArray;
    }

    private StringBuilder createMessage(int rank, String title, String content, String url) {
        return new StringBuilder()
                .append("[").append(rank).append("] ").append(title).append("\n")
                .append(content)
                .append("링크 : <").append(url).append(">").append("\n\n");
    }
}