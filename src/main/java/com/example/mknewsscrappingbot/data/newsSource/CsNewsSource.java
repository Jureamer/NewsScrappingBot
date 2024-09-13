package com.example.mknewsscrappingbot.data.newsSource;

import org.springframework.stereotype.Component;

@Component
public class CsNewsSource extends NewsSource {

    public CsNewsSource() {
        super(
                "https://www.chosun.com/",
                ".width--100.false",
                "div.story-card-component",
                "h1.article-header__headline > span",
                ".article-body"
        );
    }

    @Override
    public String getCustomRequestUrl(String category) {
        return this.getRequestUrl() + category;
    }
}