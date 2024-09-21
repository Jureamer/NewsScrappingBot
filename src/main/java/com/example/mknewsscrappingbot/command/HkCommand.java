package com.example.mknewsscrappingbot.command;

import com.example.mknewsscrappingbot.domain.keyword.HkKeywordMapping;
import com.example.mknewsscrappingbot.service.selenium.SeleniumService;

public class HkCommand extends AbstractCommand{
    public HkCommand(SeleniumService seleniumService) {
        super(seleniumService, new HkKeywordMapping(), "HK");
    }
}
