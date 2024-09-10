package com.example.mknewsscrappingbot.config;

import com.example.mknewsscrappingbot.data.KeywordMapping;
import com.example.mknewsscrappingbot.data.MessageConstants;
import com.example.mknewsscrappingbot.service.SeleniumService;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;

@Component
public class DiscordListener extends ListenerAdapter {
    private SeleniumService seleniumService;
    public DiscordListener(SeleniumService seleniumService) {
        this.seleniumService = seleniumService;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User user = event.getAuthor();
        TextChannel textChannel = event.getChannel().asTextChannel();
        Message message = event.getMessage();

        if (user.isBot()) {
            return;
        }

        String content = message.getContentDisplay();

        if (content.startsWith("!뉴스")) {
            crawlingSpecificNews(textChannel, content);
        } else {
            textChannel.sendMessage(MessageConstants.UNKNOWN_COMMAND).queue();
        }
    }

    private void crawlingSpecificNews(TextChannel textChannel, String content) {

        String[] parts = content.split("");

        if (parts.length != 1) {
            textChannel.sendMessage(MessageConstants.NEED_CATEGORY).queue();
            return;
        }

        String category = parts[1].toUpperCase();
        String categoryEn = KeywordMapping.getKeywordForCategory(category);

        if (categoryEn.isEmpty()) {
            textChannel.sendMessage(MessageConstants.AVAILABLE_CATEGORY).queue();
            return;
        }

        textChannel.sendMessage(MessageConstants.getNewsFetchingMessage(category)).queue();
        ArrayList<String> newsSummary = seleniumService.crawling(category);

        try {
            for (String summary : newsSummary) {

                textChannel.sendMessage(summary).queue();
                Thread.sleep(1);
            }
        }  catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
