package com.example.mknewsscrappingbot.data.newsSource;

import org.springframework.stereotype.Component;

@Component
public class CsNewsSource extends NewsSource {

    public CsNewsSource() {
        super(
                "https://www.hankyung.com/",
                ".allnews-list",
                "li",
                "h1.headline",
                ".article-body"
        );
    }

    @Override
    public void customizeRequestUrl() {

    }
}