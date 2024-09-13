package com.example.mknewsscrappingbot.domain;

import com.example.mknewsscrappingbot.data.newsSource.MkNewsSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;

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
