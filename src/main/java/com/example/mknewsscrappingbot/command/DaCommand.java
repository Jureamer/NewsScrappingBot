package com.example.mknewsscrappingbot.command;

import com.example.mknewsscrappingbot.domain.keyword.DaKeywordMapping;
import com.example.mknewsscrappingbot.service.selenium.SeleniumService;

public class DaCommand extends AbstractCommand{
    public DaCommand(SeleniumService seleniumService) {
        super(seleniumService, new DaKeywordMapping(), "DA");
    }
}
