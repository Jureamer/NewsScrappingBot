package com.example.mknewsscrappingbot.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MkArticleScraper implements ArticleScraper {
    private final String REQUEST_URL = "https://www.mk.co.kr/";
    private final String ARTICLE_TITLE_XPATH = "//*[@id='container']/section/div[2]/section/div/div/div/h2";
    private final String ARTICLE_CONTENT_XPATH = "//*[@id='container']/section/div[3]/section/div[1]/div[1]/div[1]";
    private final String NEWS_WRAP_CLASS = ".best_view_news_wrap";
    private final String NEWS_NODE_CSS_SELECTOR = "li.news_node";

    @Override
    public List<String> getTopUrlsByCategory(WebDriver driver, String category) {
        driver.get(REQUEST_URL + "news/" + category);
        waitForPageLoad(driver);
        waitForElementToBePresent(driver, By.cssSelector(NEWS_WRAP_CLASS));

        try {
            WebElement newsWrap = driver.findElement(By.cssSelector(NEWS_WRAP_CLASS));
            List<WebElement> newsNodes = newsWrap.findElements(By.cssSelector(NEWS_NODE_CSS_SELECTOR));
            return newsNodes.stream()
                    .map(newsNode -> newsNode.findElement(By.tagName("a")).getAttribute("href"))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String extractTitle(WebDriver driver) {
        List<WebElement> titleElements = driver.findElements(By.xpath(ARTICLE_TITLE_XPATH));
        return titleElements.get(0).getText();
    }

    @Override
    public String extractContent(WebDriver driver) {
        WebElement contentWrap = driver.findElement(By.xpath(ARTICLE_CONTENT_XPATH));
        StringBuilder content = new StringBuilder();

        List<WebElement> paragraphs = contentWrap.findElements(By.tagName("p"));

        if (!paragraphs.isEmpty()) {
            for (WebElement paragraph : paragraphs) {
                content.append(paragraph.getText()).append("\n");
                break;
            }
        } else {
            String text = contentWrap.getText();
            content.append(text.length() > 100 ? text.substring(0, 100) + "..." : text).append("\n");
        }

        return content.toString();
    }
}
