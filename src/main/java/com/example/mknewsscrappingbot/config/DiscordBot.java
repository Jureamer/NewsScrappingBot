package com.example.mknewsscrappingbot.config;

import com.example.mknewsscrappingbot.data.MessageConstants;
import com.example.mknewsscrappingbot.service.SeleniumService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DiscordBot {
    @Value("${discord.bot.token}")
    private String discordBotToken;
    private SeleniumService seleniumService;
    private JDA jda;

    public DiscordBot(SeleniumService seleniumService) {
        this.seleniumService = seleniumService;
    }

    public JDA getDiscordBot() {
        if (this.jda == null) {
            this.jda = JDABuilder.createDefault(discordBotToken)
                    .setActivity(Activity.playing(MessageConstants.WAIT_FOR_MESSAGE))
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .addEventListeners(new DiscordListener(seleniumService))
                    .build();
        }
            return jda;
    }
}
