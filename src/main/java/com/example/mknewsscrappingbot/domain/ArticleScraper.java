package com.example.mknewsscrappingbot.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public interface ArticleScraper {
    List<String> getTopUrlsByCategory(WebDriver driver, String category);

    default void waitForPageLoad(WebDriver driver) {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    default void waitForElementToBePresent(WebDriver driver, By by) {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // 대기 시간 늘리기
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (TimeoutException e) {
            System.out.println("Element not found: " + by);
            throw e;
        }
    }

    String extractTitle(WebDriver driver);
    String extractContent(WebDriver driver);
}
