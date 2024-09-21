package com.example.mknewsscrappingbot.news.scraper;

import com.example.mknewsscrappingbot.news.source.JaNewsSource;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class JaArticleScraper extends ArticleScraper {
    public JaArticleScraper(JaNewsSource newsSource) {
        super(newsSource);
    }


    @Override
    public String extractTitle(WebDriver driver) {
        return extractElementText(driver, newsSource.getArticleTitleCssSelector());
    }

    @Override
    public String extractContent(WebDriver driver) {
        return extractContent(driver, newsSource.getArticleContentCssSelector());
    }
}
