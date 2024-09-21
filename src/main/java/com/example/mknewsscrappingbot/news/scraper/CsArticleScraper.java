package com.example.mknewsscrappingbot.news.scraper;

import com.example.mknewsscrappingbot.news.source.CsNewsSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CsArticleScraper extends ArticleScraper {
    public CsArticleScraper(CsNewsSource newsSource) {
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

    @Override
    public List<String> getTopUrlsByCategory(WebDriver driver, String krCategory, String enCategory) {
        driver.get(newsSource.getCustomRequestUrl(enCategory));
        waitForPageLoad(driver);
        waitForElementToBePresent(driver, By.cssSelector(newsSource.getNewsWrapCssSelector()));

        try {
            WebElement newsWrap;

            // 연예 카테고리의 경우 두 번째 newsWrap을 사용
            System.out.println("받은 카테고리 : " + krCategory);
            if (krCategory.equals("연예")) {
                newsWrap = driver.findElements(By.cssSelector(newsSource.getNewsWrapCssSelector())).get(1);
            } else {
                newsWrap = driver.findElement(By.cssSelector(newsSource.getNewsWrapCssSelector()));
            }

            List<WebElement> newsNodes = newsWrap.findElements(By.cssSelector(newsSource.getNewsNodeCssSelector()));
            return newsNodes.stream()
                    .map(newsNode -> newsNode.findElement(By.tagName("a")).getAttribute("href"))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
