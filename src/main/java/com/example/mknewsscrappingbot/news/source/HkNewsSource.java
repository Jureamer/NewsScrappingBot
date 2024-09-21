package com.example.mknewsscrappingbot.news.source;

import org.springframework.stereotype.Component;

@Component
public class HkNewsSource extends NewsSource {
    public HkNewsSource() {
        super("https://www.hankyung.com/",
                "ul.allnews-list",
                ".thumb",
                "h1.headline",
                ".article-body"
        );
    }
}
