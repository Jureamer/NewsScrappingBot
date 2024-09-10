package com.example.mknewsscrappingbot.domain;

import com.example.mknewsscrappingbot.data.Article;
import com.example.mknewsscrappingbot.data.KeywordMapping;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeleniumService {

    private final WebDriver driver;
    private final String REQUEST_URL = "https://www.mk.co.kr/";
    private final String ARTICLE_TITLE_XPATH = "//*[@id='container']/section/div[2]/section/div/div/div/h2";
    private final String ARTICLE_CONTENT_XPATH = "//*[@id='container']/section/div[3]/section/div[1]/div[1]/div[1]";
    private final String NEWS_WRAP_CLASS = ".best_view_news_wrap";
    private final String NEWS_NODE_CSS_SELECTOR = "li.news_node";
    private final ArticleRepository articleRepository;

    public SeleniumService(SeleniumDriver seleniumDriver, ArticleRepository articleRepository) {
        this.driver = seleniumDriver.getDriver();
        this.articleRepository = articleRepository;
    }

    public ArrayList<String> crawling(String category) {
        ArrayList<String> returnMessageArray = new ArrayList<>();
        List<String> urls = getTop10Urls(category);

        try {
            int rank = 1;
            for (String url : urls) {
                driver.get(url);

                waitForPageLoad();

                String title = extractTitle();
                String content = extractContent();

                articleRepository.save(new Article("매일경제", category, rank, title, content, url));

                StringBuilder returnMessage = createMessage(rank, title, content, url);
                returnMessageArray.add(returnMessage.toString());

                rank++;

                if (rank == 6 || rank == 11) {
                    returnMessage = new StringBuilder();
                }
            }
            return returnMessageArray;
        } catch (Exception e) {
            e.printStackTrace();
            return returnMessageArray;
        }
    }

    private List<String> getTop10Urls(String category) {
        driver.get(REQUEST_URL + "news/" + category);
        waitForPageLoad();
        waitForElementToBePresent(By.cssSelector(NEWS_WRAP_CLASS));

        try {
            WebElement newsWrap = driver.findElement(By.cssSelector(NEWS_WRAP_CLASS));
            List<WebElement> newsNodes = newsWrap.findElements(By.cssSelector(NEWS_NODE_CSS_SELECTOR));
            return newsNodes.stream()
                    .map(newsNode -> newsNode.findElement(By.tagName("a")).getAttribute("href"))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void waitForPageLoad() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    private void waitForElementToBePresent(By by) {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // 대기 시간 늘리기
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (TimeoutException e) {
            System.out.println("Element not found: " + by);
            throw e;
        }
        }

    private String extractTitle() {
        List<WebElement> titleElements = driver.findElements(By.xpath(ARTICLE_TITLE_XPATH));
        return titleElements.get(0).getText();
    }

    private String extractContent() {
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


    private StringBuilder createMessage(int rank, String title, String content, String url) {
        return new StringBuilder()
                .append("[").append(rank).append("] ").append(title).append("\n")
                .append("내용 : ").append("\n")
                .append(content).append("\n")
                .append("링크 : <").append(url).append(">").append("\n");
    }
}