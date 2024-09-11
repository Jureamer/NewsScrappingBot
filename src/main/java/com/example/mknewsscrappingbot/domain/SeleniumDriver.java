package com.example.mknewsscrappingbot.domain;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.stereotype.Component;

@Component
public class SeleniumDriver {
    private WebDriver driver = null;
    public static String WEB_DRIVER_ID = "webdriver.gecko.driver";
    public static String WEB_DRIVER_PATH = "/usr/local/bin/geckodriver";

    public WebDriver getDriver() {
        if (driver == null) {
            System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

            FirefoxOptions options = new FirefoxOptions();

            options.addArguments("--start-maximized"); //최대크기로
            options.addArguments("--headless"); // Browser를 띄우지 않음
            options.addArguments("--disable-gpu"); // GPU를 사용하지 않음, Linux에서 headless를 사용하는 경우 필요함.
            options.addArguments("--no-sandbox"); // Sandbox 프로세스를 사용하지 않음, Linux에서 headless를 사용하는 경우 필요함.
            options.addArguments("--disable-popup-blocking"); //팝업 무시
            options.addArguments("--disable-default-apps"); // 기본앱 사용안함
            driver = new FirefoxDriver(options);
            return driver;
        } else {
            return driver;
        }
    }
}
