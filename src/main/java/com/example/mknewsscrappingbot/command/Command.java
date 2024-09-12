package com.example.mknewsscrappingbot.command;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public interface Command {
    void execute(TextChannel channel, String content);
}
