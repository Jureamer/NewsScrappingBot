package com.example.mknewsscrappingbot;

import com.example.mknewsscrappingbot.service.SeleniumService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MkNewsScrappingBotApplication {
    public static void main(String[] args) {
        SeleniumService seleniumService = new SeleniumService();
        seleniumService.testSelenium();
    }
}
