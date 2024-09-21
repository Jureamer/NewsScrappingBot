package com.example.mknewsscrappingbot.service.discord;

import com.example.mknewsscrappingbot.command.*;
import com.example.mknewsscrappingbot.common.constant.MessageConstants;
import com.example.mknewsscrappingbot.common.IKeywordMapping;

import com.example.mknewsscrappingbot.service.selenium.SeleniumService;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class DiscordListener extends ListenerAdapter {
    private static final String MK_START_COMMAND = "/mk";
    private static final String HK_START_COMMAND = "/hk";
    private static final String CS_START_COMMAND = "/cs";
    private static final String JA_START_COMMAND = "/ja";
    private static final String DA_START_COMMAND = "/da";
    private static final String HELP_START_COMMAND = "/help";
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
        Command command;

        if (content.startsWith(MK_START_COMMAND)) {
            command = new MkCommand(seleniumService);
        } else if(content.startsWith(HK_START_COMMAND)) {
            command = new HkCommand(seleniumService);
        } else if(content.startsWith(CS_START_COMMAND)) {
            command = new CsCommand(seleniumService);
        } else if(content.startsWith(JA_START_COMMAND)) {
            command = new JaCommand(seleniumService);
        } else if(content.startsWith(DA_START_COMMAND)) {
            command = new DaCommand(seleniumService);
        } else if(content.startsWith(HELP_START_COMMAND)) {
            command = new HelpCommand();
        } else {
            textChannel.sendMessage(MessageConstants.UNKNOWN_COMMAND).queue();
            return;
        }
        command.execute(textChannel, content);
    }
}
