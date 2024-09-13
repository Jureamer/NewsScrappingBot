package com.example.mknewsscrappingbot.command;

import com.example.mknewsscrappingbot.data.keywordMapping.HkKeywordMapping;
import com.example.mknewsscrappingbot.domain.SeleniumService;

public class HkCommand extends AbstractCommand{
    public HkCommand(SeleniumService seleniumService) {
        super(seleniumService, new HkKeywordMapping(), "HK");
    }
}
