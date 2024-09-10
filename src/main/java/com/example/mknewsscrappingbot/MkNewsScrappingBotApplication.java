package com.example.mknewsscrappingbot;

import com.example.mknewsscrappingbot.config.DiscordBot;
import com.example.mknewsscrappingbot.config.DiscordBotToken;
import com.example.mknewsscrappingbot.service.SeleniumService;
import net.dv8tion.jda.api.JDA;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MkNewsScrappingBotApplication {
    private DiscordBotToken discordBotToken;
    private SeleniumService seleniumService;

    @Autowired
    public MkNewsScrappingBotApplication(DiscordBotToken discordBotToken, SeleniumService seleniumService) {
        this.discordBotToken = discordBotToken;
        this.seleniumService = seleniumService;
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MkNewsScrappingBotApplication.class, args);
        MkNewsScrappingBotApplication application = context.getBean(MkNewsScrappingBotApplication.class);
        JDA jda = application.initializeBot();
    }

    @Bean
    public JDA initializeBot() {
        return new DiscordBot(discordBotToken, seleniumService).getDiscordBot();
    }
}
