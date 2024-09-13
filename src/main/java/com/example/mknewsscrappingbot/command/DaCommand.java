package com.example.mknewsscrappingbot.command;

import com.example.mknewsscrappingbot.data.keywordMapping.DaKeywordMapping;
import com.example.mknewsscrappingbot.domain.SeleniumService;

public class DaCommand extends AbstractCommand{
    public DaCommand(SeleniumService seleniumService) {
        super(seleniumService, new DaKeywordMapping(), "DA");
    }
}
