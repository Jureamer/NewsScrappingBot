package com.example.mknewsscrappingbot.command;

import com.example.mknewsscrappingbot.data.MkKeywordMapping;
import com.example.mknewsscrappingbot.domain.SeleniumService;

public class CsCommand extends AbstractCommand{
    public CsCommand(SeleniumService seleniumService) {
        super(seleniumService, new MkKeywordMapping(), "CS");
    }
}
