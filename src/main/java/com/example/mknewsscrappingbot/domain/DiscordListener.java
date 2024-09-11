package com.example.mknewsscrappingbot.domain;

import com.example.mknewsscrappingbot.data.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class DiscordListener extends ListenerAdapter {
    private static final String MK_START_COMMAND = "/mk";
    private static final String CS_START_COMMAND = "/cs";
    private static final String JA_START_COMMAND = "/ja";
    private final SeleniumService seleniumService;
    private IKeywordMapping keywordMapping;

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

        if (!textChannel.canTalk()) {
            textChannel.sendMessage("기존 메세지를 처리 중입니다. 잠시만 기다려주세요").queue();
            return;
        }

        String content = message.getContentDisplay();


        if (content.startsWith(MK_START_COMMAND)) {
            keywordMapping = new MkKeywordMapping();
            crawlingSpecificNews(textChannel, content, "MK");
        } else if(content.startsWith(CS_START_COMMAND)) {
            keywordMapping = new CsKeywordMapping();
            crawlingSpecificNews(textChannel, content, "CS");
        }else if(content.startsWith(JA_START_COMMAND)) {
            keywordMapping = new JaKeywordMapping();
            crawlingSpecificNews(textChannel, content, "JA");
        } else {
            textChannel.sendMessage(MessageConstants.UNKNOWN_COMMAND).queue();
        }
    }

    private void crawlingSpecificNews(TextChannel textChannel, String content, String media) {
        String[] parts = content.split(" ");

        if (parts.length != 2) {
            textChannel.sendMessage(MessageConstants.NEED_CATEGORY).queue();
            return;
        }

        String category = parts[1].toUpperCase();
        String categoryEn = keywordMapping.getKeywordForCategory(category);

        if (categoryEn.isEmpty()) {
            textChannel.sendMessage(MessageConstants.getAvailableCategoryMessage(keywordMapping.toKeyString())).queue();
            return;
        }

        textChannel.sendMessage(MessageConstants.getNewsFetchingMessage(keywordMapping.getKrName(), category)).queue();
        ArrayList<EmbedBuilder> newsSummary = seleniumService.crawling(media, categoryEn);

        textChannel.sendMessage("여기 뉴스 내용입니다:")
                .addEmbeds(newsSummary.stream()
                .map(EmbedBuilder::build)
                .collect(Collectors.toList()))
                .queue();
    }
}
