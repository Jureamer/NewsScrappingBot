package com.example.mknewsscrappingbot.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class DaArticleScraper implements ArticleScraper {
    private final String REQUEST_URL = "https://www.donga.com/";
    private final String NEWS_WRAP_CSS_SELECTOR = "div.sec_body";
    private final String NEWS_NODE_CSS_SELECTOR = "li";
    private final String ARTICLE_TITLE_CSS_SELECTOR = "section.head_group h1";
    private final String ARTICLE_CONTENT_CSS_SELECTOR = "section.news_view";

    @Override
    public List<String> getTopUrlsByCategory(WebDriver driver, String category) {
        driver.get(REQUEST_URL + "news/" + category);
        waitForPageLoad(driver);
        waitForElementToBePresent(driver, By.cssSelector(NEWS_WRAP_CSS_SELECTOR));

        try {
            WebElement newsWrap = driver.findElement(By.cssSelector(NEWS_WRAP_CSS_SELECTOR));
            List<WebElement> newsNodes = newsWrap.findElements(By.cssSelector(NEWS_NODE_CSS_SELECTOR));
            return newsNodes.stream()
                    .map(newsNode -> {
                        try {
                            return newsNode.findElement(By.tagName("a")).getAttribute("href");
                        } catch (NoSuchElementException e) {
                            return null;
                        }
                    }).filter(Objects::nonNull)
                    .toList();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("뉴스 목록을 찾을 수 없습니다: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("알 수 없는 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public String extractTitle(WebDriver driver) {
        WebElement titleElement = driver.findElement(By.cssSelector(ARTICLE_TITLE_CSS_SELECTOR));
        return titleElement.getText();
    }

    @Override
    public String extractContent(WebDriver driver) {
        WebElement contentWrap = driver.findElement(By.cssSelector(ARTICLE_CONTENT_CSS_SELECTOR));
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
