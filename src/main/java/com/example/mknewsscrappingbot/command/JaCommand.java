package com.example.mknewsscrappingbot.command;

import com.example.mknewsscrappingbot.data.keywordMapping.JaKeywordMapping;
import com.example.mknewsscrappingbot.domain.SeleniumService;

public class JaCommand extends AbstractCommand{
    public JaCommand(SeleniumService seleniumService) {
        super(seleniumService, new JaKeywordMapping(), "JA");
    }
}
