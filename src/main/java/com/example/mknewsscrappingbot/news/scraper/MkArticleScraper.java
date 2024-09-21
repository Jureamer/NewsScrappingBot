package com.example.mknewsscrappingbot.news.scraper;

import com.example.mknewsscrappingbot.news.source.MkNewsSource;
import com.example.mknewsscrappingbot.news.scraper.ArticleScraper;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class MkArticleScraper extends ArticleScraper {

    public MkArticleScraper(MkNewsSource newsSource) {
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
