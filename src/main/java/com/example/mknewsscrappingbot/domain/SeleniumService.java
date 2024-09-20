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

    public ArrayList<EmbedBuilder> getData(String media, String category) {
        ArrayList<EmbedBuilder> returnMessageArray = new ArrayList<>();
        int rank = 1;

//        List<Article> existingArticles = articleRepository.findByCategoryOrderByRank(category);
        List<Article> existingArticles = articleRepository.findByMediaAndCategoryOrderByRank(media, category);

        System.out.println("existingArticles.size() = " + existingArticles.size());
        for (Article article : existingArticles) {
            EmbedBuilder message = createEmbedMessage(rank, article.getTitle(), article.getContent(), article.getUrl());
            returnMessageArray.add(message);
            rank++;
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