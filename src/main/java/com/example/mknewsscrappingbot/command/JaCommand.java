package com.example.mknewsscrappingbot.command;

import com.example.mknewsscrappingbot.domain.keyword.JaKeywordMapping;
import com.example.mknewsscrappingbot.service.selenium.SeleniumService;

public class JaCommand extends AbstractCommand{
    public JaCommand(SeleniumService seleniumService) {
        super(seleniumService, new JaKeywordMapping(), "JA");
    }
}
