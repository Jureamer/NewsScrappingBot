package com.example.mknewsscrappingbot;

import com.example.mknewsscrappingbot.domain.DiscordBot;
import com.example.mknewsscrappingbot.domain.SeleniumService;
import net.dv8tion.jda.api.JDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MkNewsScrappingBotApplication {
    private final SeleniumService seleniumService;
    private final DiscordBot discordBot;


    @Autowired
    public MkNewsScrappingBotApplication(SeleniumService seleniumService, DiscordBot discordBot) {
        this.seleniumService = seleniumService;
        this.discordBot = discordBot;
    }


    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MkNewsScrappingBotApplication.class, args);
    }

    @Bean
    public JDA jda() {
        return discordBot.getDiscordBot();
    }
}
