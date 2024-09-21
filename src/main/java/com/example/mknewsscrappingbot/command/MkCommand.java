package com.example.mknewsscrappingbot.command;

import com.example.mknewsscrappingbot.domain.keyword.MkKeywordMapping;
import com.example.mknewsscrappingbot.service.selenium.SeleniumService;

public class MkCommand extends AbstractCommand{
    public MkCommand(SeleniumService seleniumService) {
        super(seleniumService, new MkKeywordMapping(), "MK");
    }
}
