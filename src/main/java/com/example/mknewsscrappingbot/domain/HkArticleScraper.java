package com.example.mknewsscrappingbot.domain;

import com.example.mknewsscrappingbot.data.newsSource.HkNewsSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HkArticleScraper extends ArticleScraper {
    public HkArticleScraper(HkNewsSource newsSource) {
        super(newsSource);
    }

    @Override
    public List<String> getTopUrlsByCategory(WebDriver driver, String category) {
        driver.get(newsSource.getRequestUrl() + "news/" + category);
        waitForPageLoad(driver);
        waitForElementToBePresent(driver, By.cssSelector(newsSource.getNewsWrapCssSelector()));

        try {
            WebElement newsWrap = driver.findElement(By.cssSelector(newsSource.getNewsWrapCssSelector()));
            List<WebElement> newsNodes = newsWrap.findElements(By.cssSelector(newsSource.getNewsNodeCssSelector()));
            return newsNodes.stream()
                    .map(newsNode -> newsNode.findElement(By.tagName("a")).getAttribute("href"))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
