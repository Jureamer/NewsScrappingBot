package com.example.mknewsscrappingbot.domain;

import com.example.mknewsscrappingbot.data.KeywordMapping;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeleniumService {

    private final WebDriver driver = new SeleniumDriver().getDriver();

    public ArrayList<String> crawling(String category) {
        String requestUrl = "https://www.mk.co.kr/";
        ArrayList<String> returnMessageArray = new ArrayList<>();
        StringBuilder returnMessage = new StringBuilder();

        try {
            String categoryEn = KeywordMapping.getKeywordForCategory(category);
            driver.get(requestUrl + "news/" + categoryEn);

            Thread.sleep(1000); // 페이지 로딩 시간 동안 기다림

            // Top 10 뉴스 항목 선택
            WebElement newsWrap = driver.findElement(By.className("best_view_news_wrap"));
            List<WebElement> newsNodes = newsWrap.findElements(By.cssSelector("li.news_node"));
            List<String> urls = newsNodes.stream()
                    .map(newsNode -> newsNode.findElement(By.tagName("a")).getAttribute("href"))
                    .toList();

            int rank = 1;

            for (String url : urls) {
                // 뉴스 페이지로 이동
                driver.get(url);
                // 페이지 전체가 로드될 때까지 대기
                new WebDriverWait(driver, Duration.ofSeconds(20)).until(
                        webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
                );

                // 제목 추출
                List<WebElement> titleElements = driver.findElements(By.xpath("//*[@id='container']/section/div[2]/section/div/div/div/h2"));
                String title = titleElements.get(0).getText();

                // 내용 추출
                WebElement contentWrap = driver.findElement(By.xpath("//*[@id='container']/section/div[3]/section/div[1]/div[1]/div[1]"));
                StringBuilder content = new StringBuilder();

                List<WebElement> paragraphs = contentWrap.findElements(By.tagName("p"));

                if (!paragraphs.isEmpty()) {
                    for (WebElement paragraph : paragraphs) {
                        content.append(paragraph.getText()).append("\n");
                        break;
                    }
                } else {
                    content.append(contentWrap.getText().substring(0, 100) + "...").append("\n");
                }

                returnMessage.append("순위 : ").append("[").append(rank).append("]").append("\n");
                returnMessage.append("제목 : ").append(title).append("\n");
                returnMessage.append("내용 : ").append(content);
                returnMessage.append("링크 : ").append("<").append(url).append(">").append("\n");

                rank += 1;

                if (rank == 6 || rank == 11) {
                    returnMessageArray.add(returnMessage.toString());
                    returnMessage.setLength(0);
                }
            }
            return returnMessageArray;
        } catch (Exception e) {
            e.printStackTrace();
            return returnMessageArray;
        } finally {
            driver.quit();
        }
    }
}