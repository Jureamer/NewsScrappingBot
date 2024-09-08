package com.example.mknewsscrappingbot.config;

import com.example.mknewsscrappingbot.service.SeleniumService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscordBot {
    private DiscordBotToken discordBotTokenEntity;
    private String discordBotToken;
    private SeleniumService seleniumService;
    private JDA jda;
    public DiscordBot(DiscordBotToken discordBotTokenEntity, SeleniumService seleniumService) {
        this.discordBotTokenEntity = discordBotTokenEntity;
        this.seleniumService = seleniumService;
    }

    public JDA getDiscordBot() {
        if (this.jda == null) {
            this.discordBotToken = this.discordBotTokenEntity.getDiscordBotToken();
            this.jda = JDABuilder.createDefault(this.discordBotToken)
                    .setActivity(Activity.playing("메시지 대기 중"))
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .addEventListeners(new DiscordListener(seleniumService))
                    .build();
        }
            return jda;
    }
}
