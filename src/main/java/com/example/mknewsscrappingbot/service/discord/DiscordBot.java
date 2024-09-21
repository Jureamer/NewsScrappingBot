package com.example.mknewsscrappingbot.service.discord;

import com.example.mknewsscrappingbot.common.constant.ErrorConstants;
import com.example.mknewsscrappingbot.common.constant.MessageConstants;
import com.example.mknewsscrappingbot.service.selenium.SeleniumService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DiscordBot {
    @Value("${discord.bot.token}")
    private String discordBotToken;
    private SeleniumService seleniumService;
    private DiscordListener discordListener;
    private JDA jda;

    public DiscordBot(SeleniumService seleniumService, DiscordListener discordListener) {
        this.seleniumService = seleniumService;
        this.discordListener = discordListener;
    }

    public JDA getDiscordBot() {
        if (discordBotToken == null || discordBotToken.isEmpty()) {
            throw new IllegalStateException(ErrorConstants.DISCORD_BOT_TOKEN_IS_NOT_SET);
        }

        if (this.jda == null) {
            this.jda = JDABuilder.createDefault(discordBotToken)
                .setActivity(Activity.playing(MessageConstants.WAIT_FOR_MESSAGE))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(discordListener)
                .build();
        }

        return jda;
    }
}
