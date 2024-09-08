package com.example.mknewsscrappingbot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DiscordBotToken {
    @Value("${discord.bot.token}")
    private String discordBotToken;

    public String getDiscordBotToken() {
        System.out.println("discordBotToken: " + this.discordBotToken);
        return this.discordBotToken;
    }
}
