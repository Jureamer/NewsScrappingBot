package com.example.mknewsscrappingbot.config;

import com.example.mknewsscrappingbot.service.SeleniumService;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

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
            String[] parts = content.split(" ");
            if (parts.length > 1) {
                String category = parts[1];
                String newsSummary = seleniumService.crawling(category);
                textChannel.sendMessage(newsSummary).queue();
            } else {
                textChannel.sendMessage("카테고리를 입력해 주세요. 예: !뉴스 경제").queue();
            }

        }
    }

    private String sendMessage(MessageReceivedEvent event, String message) {
        User user = event.getAuthor();
        String returnMessage = "";

        switch (message) {
            case "뉴스":
                returnMessage = "뉴스를 가져옵니다.";
                break;
            default:
                returnMessage = "알 수 없는 명령어입니다.";
                break;
        }
            return message;
    }
}
