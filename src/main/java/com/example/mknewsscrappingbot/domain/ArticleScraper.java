package com.example.mknewsscrappingbot.domain;

import com.example.mknewsscrappingbot.data.newsSource.NewsSource;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class ArticleScraper {
    protected NewsSource newsSource;

    public ArticleScraper(NewsSource newsSource) {
        this.newsSource = newsSource;
    }

    protected void waitForPageLoad(WebDriver driver) {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    protected void waitForElementToBePresent(WebDriver driver, By by) {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // 대기 시간 늘리기
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (TimeoutException e) {
            System.out.println("Element not found: " + by);
            throw e;
        }
    }

    protected List<String> getTopUrlsByCategory(WebDriver driver, String category) {
        driver.get(newsSource.getCustomRequestUrl(category));
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


    public String extractElementText(WebDriver driver, String cssSelector) {
        WebElement element = driver.findElement(By.cssSelector(cssSelector));
        return element.getText();
    }


    public String extractContent(WebDriver driver, String cssSelector) {
        WebElement contentWrap = driver.findElement(By.cssSelector(cssSelector));
        StringBuilder content = new StringBuilder();

        List<WebElement> paragraphs = contentWrap.findElements(By.tagName("p"));

        if (!paragraphs.isEmpty()) {
            for (WebElement paragraph : paragraphs) {
                content.append(paragraph.getText()).append("\n");
            }
        } else {
            content.append(contentWrap.getText()).append("\n");
        }

        return content.toString();
    }

    public abstract String extractTitle(WebDriver driver);

    public abstract String extractContent(WebDriver driver);
}
