package com.example.mknewsscrappingbot.command;

import com.example.mknewsscrappingbot.data.keywordMapping.MkKeywordMapping;
import com.example.mknewsscrappingbot.domain.SeleniumService;

public class MkCommand extends AbstractCommand{
    public MkCommand(SeleniumService seleniumService) {
        super(seleniumService, new MkKeywordMapping(), "MK");
    }
}
