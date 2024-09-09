package com.example.mknewsscrappingbot.service;

import com.example.mknewsscrappingbot.data.KeywordMapping;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

@Service
public class SeleniumService {
    private WebDriver driver;
    public static String WEB_DRIVER_ID = "webdriver.gecko.driver";
//    public static String WEB_DRIVER_PATH = "/Users/han/Downloads/geckodriver";
    public static String WEB_DRIVER_PATH = "/usr/bin/geckodriver";

    public String crawling(String category) {

        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        // 2. WebDriver 옵션 설정
        FirefoxOptions options = new FirefoxOptions();

        options.addArguments("--start-maximized"); //최대크기로
        options.addArguments("--headless"); // Browser를 띄우지 않음
        options.addArguments("--disable-gpu"); // GPU를 사용하지 않음, Linux에서 headless를 사용하는 경우 필요함.
        options.addArguments("--no-sandbox"); // Sandbox 프로세스를 사용하지 않음, Linux에서 headless를 사용하는 경우 필요함.
        options.addArguments("--disable-popup-blocking"); //팝업 무시
        options.addArguments("--disable-default-apps"); // 기본앱 사용안함
        driver = new FirefoxDriver(options);

        String requestUrl = "https://www.mk.co.kr/";
        String returnMessage = "";

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

                // 각 뉴스 항목을 순회하면서 제목과 내용을 추출
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
                        content.append(contentWrap.getText()).append("\n");
                    }

//                    System.out.println("content : " + content);
                    returnMessage += "제목 : " + title + "\n" + "내용 :" + content + "\n";
                }
            return returnMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            driver.quit();
        }
    }
}