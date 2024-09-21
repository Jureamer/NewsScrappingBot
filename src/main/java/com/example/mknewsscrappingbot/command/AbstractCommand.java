package com.example.mknewsscrappingbot.command;

import com.example.mknewsscrappingbot.constant.MessageConstants;
import com.example.mknewsscrappingbot.data.keywordMapping.IKeywordMapping;
import com.example.mknewsscrappingbot.domain.SeleniumService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class AbstractCommand implements Command {
    private final SeleniumService seleniumService;
    private final IKeywordMapping keywordMapping;
    private final String media;

    protected AbstractCommand(SeleniumService seleniumService, IKeywordMapping keywordMapping, String media) {
        this.seleniumService = seleniumService;
        this.keywordMapping = keywordMapping;
        this.media = media;
    }

    @Override
    public void execute(TextChannel textChannel, String content) {
        processCrawling(textChannel, content);
    }

    private void processCrawling(TextChannel textChannel, String content) {
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

        textChannel.sendMessage((MessageConstants.getNewsFetchingMessage(keywordMapping.getKrName(), category))).queue();
        ArrayList<EmbedBuilder> newsSummery = seleniumService.getData(media, category);

        textChannel.sendMessage(MessageConstants.NEWS_PRINT_INFO)
                .addEmbeds(newsSummery.stream()
                .map(EmbedBuilder::build)
                .collect(Collectors.toList()))
                .queue();
    }
}
