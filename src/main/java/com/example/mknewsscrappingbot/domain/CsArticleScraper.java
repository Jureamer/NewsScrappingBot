package com.example.mknewsscrappingbot.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CsArticleScraper implements ArticleScraper {
    private final String REQUEST_URL = "https://www.chosun.com/";
    private final String ARTICLE_TITLE_XPATH = "//*[@id=\"fusion-app\"]/div[1]/div[2]/div/div/div[3]/h1/span";
    private final String ARTICLE_CONTENT_CLASS = "article-body";
    private final String NEWS_WRAP_CSS_SELECTOR = ".width--100.false";
    private final String NEWS_NODE_CSS_SELECTOR = ".flex.flex--justify-space-between";

    @Override
    public List<String> getTopUrlsByCategory(WebDriver driver, String category) {
        driver.get(REQUEST_URL + category);
        waitForPageLoad(driver);
        waitForElementToBePresent(driver, By.cssSelector(NEWS_WRAP_CSS_SELECTOR));

        try {
            WebElement newsWrap = driver.findElement(By.cssSelector(NEWS_WRAP_CSS_SELECTOR));
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
        WebElement contentWrap = driver.findElement(By.className(ARTICLE_CONTENT_CLASS));
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
