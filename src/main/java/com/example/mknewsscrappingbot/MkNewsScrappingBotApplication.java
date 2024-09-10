package com.example.mknewsscrappingbot;

import com.example.mknewsscrappingbot.config.DiscordBot;
import com.example.mknewsscrappingbot.service.SeleniumService;
import net.dv8tion.jda.api.JDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MkNewsScrappingBotApplication {
    private SeleniumService seleniumService;

    @Autowired
    public MkNewsScrappingBotApplication(SeleniumService seleniumService) {
        this.seleniumService = seleniumService;
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MkNewsScrappingBotApplication.class, args);
        MkNewsScrappingBotApplication application = context.getBean(MkNewsScrappingBotApplication.class);
        JDA jda = application.initializeBot();
    }

    @Bean
    public JDA initializeBot() {
        return new DiscordBot(seleniumService).getDiscordBot();
    }
}
