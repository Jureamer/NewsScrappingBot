package com.example.mknewsscrappingbot.domain;

import com.example.mknewsscrappingbot.data.Article;
import net.dv8tion.jda.api.EmbedBuilder;
import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeleniumService {

    private final WebDriver driver;
    private final ArticleRepository articleRepository;
    private final MkArticleScraper mkArticleScraper;
    private final CsArticleScraper csArticleScraper;
    private final JaArticleScraper jaArticleScraper;
    private final DaArticleScraper daArticleScraper;
    private ArticleScraper articleScraper;

    public SeleniumService(
            SeleniumDriver seleniumDriver,
            ArticleRepository articleRepository,
            @Qualifier("mkArticleScraper") MkArticleScraper mkArticleScraper,
            @Qualifier("csArticleScraper") CsArticleScraper csArticleScraper,
            @Qualifier("jaArticleScraper") JaArticleScraper jaArticleScraper,
            @Qualifier("daArticleScraper") DaArticleScraper daArticleScraper,
            @Qualifier("mkArticleScraper") ArticleScraper articleScraper) {
        this.driver = seleniumDriver.getDriver();
        this.articleRepository = articleRepository;
        this.mkArticleScraper = mkArticleScraper;
        this.csArticleScraper = csArticleScraper;
        this.jaArticleScraper = jaArticleScraper;
        this.daArticleScraper = daArticleScraper;
        this.articleScraper =  articleScraper;
    }

    public ArrayList<EmbedBuilder> crawling(String media, String category) {
        if (media.equals("MK")) {
            articleScraper = mkArticleScraper;
        } else if(media.equals("CS")) {
            articleScraper = csArticleScraper;
        } else if(media.equals("JA")) {
            articleScraper = jaArticleScraper;
        } else if(media.equals("DA")) {
            articleScraper = daArticleScraper;
        }

        ArrayList<EmbedBuilder> returnMessageArray = new ArrayList<>();

        int rank = 1;

        List<Article> existingArticles = articleRepository.findByCategoryOrderByRank(category);

        for (Article article : existingArticles) {
            EmbedBuilder message = createEmbedMessage(rank, article.getTitle(), article.getContent(), article.getUrl());
            returnMessageArray.add(message);
            rank++;
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

                    EmbedBuilder message = createEmbedMessage(rank, title, content, url);
                    returnMessageArray.add(message);
                    rank++;

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

    private EmbedBuilder createEmbedMessage(int rank, String title, String content, String url) {
        EmbedBuilder eb = new EmbedBuilder()
                .setTitle("[" + rank + "]" + title)
                .setColor(Color.YELLOW)
                .setDescription(content)
                .addField("링크", "[클릭하여 보기](" + url + ")", true);
        return eb;
    }
}