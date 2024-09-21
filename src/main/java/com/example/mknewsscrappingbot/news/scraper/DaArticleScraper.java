package com.example.mknewsscrappingbot.news.scraper;

import com.example.mknewsscrappingbot.news.source.DaNewsSource;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class DaArticleScraper extends ArticleScraper {

    public DaArticleScraper(DaNewsSource newsSource) {
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
