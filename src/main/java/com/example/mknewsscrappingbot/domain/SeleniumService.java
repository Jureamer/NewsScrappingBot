package com.example.mknewsscrappingbot.domain;

import com.example.mknewsscrappingbot.data.Article;
import dev.langchain4j.model.chat.ChatLanguageModel;
import net.dv8tion.jda.api.EmbedBuilder;
import org.openqa.selenium.*;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeleniumService {

    private final WebDriver driver;
    private final ArticleRepository articleRepository;
    private final ArticleScraperFactory articleScraperFactory;
    private final ChatService chatService;

    public SeleniumService(
            SeleniumDriver seleniumDriver,
            ArticleRepository articleRepository,
            ArticleScraperFactory articleScraperFactory,
            ChatService chatService) {
        this.driver = seleniumDriver.getDriver();
        this.articleRepository = articleRepository;
        this.articleScraperFactory = articleScraperFactory;
        this.chatService = chatService;
    }

    public ArrayList<EmbedBuilder> crawling(String media, String category) {
        ArticleScraper articleScraper = articleScraperFactory.getArticleScraper(media);
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

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return returnMessageArray;
    }

    private EmbedBuilder createEmbedMessage(int rank, String title, String content, String url) {
        EmbedBuilder eb = new EmbedBuilder()
                .setTitle("[" + rank + "] " + title)
                .setColor(Color.YELLOW)
                .setDescription(content)
                .addField("링크", "[클릭하여 보기](" + url + ")", true);
        return eb;
    }
}