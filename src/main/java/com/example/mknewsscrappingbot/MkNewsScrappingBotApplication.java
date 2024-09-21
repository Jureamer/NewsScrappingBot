package com.example.mknewsscrappingbot;

import com.example.mknewsscrappingbot.service.discord.DiscordBot;
import net.dv8tion.jda.api.JDA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MkNewsScrappingBotApplication {
    private final DiscordBot discordBot;

    public MkNewsScrappingBotApplication(DiscordBot discordBot) {
        this.discordBot = discordBot;
    }
    public static void main(String[] args) {
        SpringApplication.run(MkNewsScrappingBotApplication.class, args);
    }

    @Bean
    public JDA jda() {
        return discordBot.getDiscordBot();
    }

}
