package com.example.mknewsscrappingbot.news.scraper;

import com.example.mknewsscrappingbot.news.source.HkNewsSource;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class HkArticleScraper extends ArticleScraper {
    public HkArticleScraper(HkNewsSource newsSource) {
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
