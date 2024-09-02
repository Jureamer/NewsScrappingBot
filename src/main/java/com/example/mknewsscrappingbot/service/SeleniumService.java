package com.example.mknewsscrappingbot.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SeleniumService {
    private WebDriver driver;
    private WebElement element;
    public static String WEB_DRIVER_ID = "webdriver.gecko.driver";
    public static String WEB_DRIVER_PATH = "/Users/han/Downloads/geckodriver";
    public void testSelenium() {

        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        // 2. WebDriver 옵션 설정
        FirefoxOptions options = new FirefoxOptions();

        options.addArguments("--start-maximized"); //최대크기로
//        options.addArguments("--headless"); // Browser를 띄우지 않음
        options.addArguments("--disable-gpu"); // GPU를 사용하지 않음, Linux에서 headless를 사용하는 경우 필요함.
        options.addArguments("--no-sandbox"); // Sandbox 프로세스를 사용하지 않음, Linux에서 headless를 사용하는 경우 필요함.
        options.addArguments("--disable-popup-blocking"); //팝업 무시
        options.addArguments("--disable-default-apps"); // 기본앱 사용안함
        driver = new FirefoxDriver(options);

        String requestUrl = "https://www.mk.co.kr/";

        try {
            driver.get(requestUrl);
            Thread.sleep(2000); // 페이지 로딩 시간 동안 기다림

            // Top 10 뉴스 항목 선택
            List<WebElement> newsNodes = driver.findElements(By.cssSelector("li.news_node.top_news_node"));
            List<String> urls = newsNodes.stream()
                    .map(newsNode -> newsNode.findElement(By.tagName("a")).getAttribute("href"))
                    .toList();

            // 각 뉴스 항목을 순회하면서 제목과 내용을 추출
            for (String url : urls) {

                // 뉴스 페이지로 이동
                driver.get(url);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h2.news_ttl")));

                // 제목 추출
                WebElement titleElement = driver.findElement(By.cssSelector("h2.news_ttl"));
                String title = titleElement.getText();
                System.out.println("Title: " + title);

                // 내용 추출
                WebElement contentWrap = driver.findElement(By.cssSelector("div.news_cnt_detail_wrap"));
                List<WebElement> paragraphs = contentWrap.findElements(By.tagName("p"));
                StringBuilder content = new StringBuilder();
                for (WebElement paragraph : paragraphs) {
                    content.append(paragraph.getText()).append("\n");
                }
                System.out.println("Content: " + content.toString());
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
