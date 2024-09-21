package com.example.mknewsscrappingbot.command;

import com.example.mknewsscrappingbot.domain.keyword.CsKeywordMapping;
import com.example.mknewsscrappingbot.service.selenium.SeleniumService;

public class CsCommand extends AbstractCommand{
    public CsCommand(SeleniumService seleniumService) {
        super(seleniumService, new CsKeywordMapping(), "CS");
    }
}
