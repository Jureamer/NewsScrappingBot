package com.example.mknewsscrappingbot.config;

import com.example.mknewsscrappingbot.data.KeywordMapping;
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
            String[] parts = content.split(" ");
            if (parts.length > 1) {
                String category = parts[1];
                String categoryEn = KeywordMapping.getKeywordForCategory(category);

                if (categoryEn.isEmpty()) {
                    textChannel.sendMessage("사용 가능한 카테고리: 경제, 비즈니스, IT, 사회, 세계, 부동산, 주식, 정치, 문화, 스포츠 ").queue();
                }
                textChannel.sendMessage(category + "뉴스를 가져옵니다.").queue();
                ArrayList<String> newsSummary = seleniumService.crawling(category);

                try {
                    for (String summary : newsSummary) {
                        textChannel.sendMessage(summary).queue();
                        Thread.sleep(1);
                    }
                }  catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                textChannel.sendMessage("카테고리를 입력해 주세요. 예: !뉴스 경제").queue();
            }
        }
    }

    private String sendMessage(String message) {
            return message;
    }
}
