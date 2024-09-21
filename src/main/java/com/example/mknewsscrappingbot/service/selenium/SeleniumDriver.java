package com.example.mknewsscrappingbot.service.selenium;

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

            options.addArguments("--start-maximized");
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-default-apps");
            driver = new FirefoxDriver(options);
            return driver;
        } else {
            return driver;
        }
    }
}
