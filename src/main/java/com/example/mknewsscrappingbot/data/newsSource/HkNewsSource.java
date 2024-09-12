package com.example.mknewsscrappingbot.data.newsSource;

public class HkNewsSource extends NewsSource {
    public HkNewsSource() {
        super("https://www.hankookilbo.com/",
                ".article-list",
                "li",
                "h1.headline",
                ".article-body"
        );
    }

    @Override
    public void customizeRequestUrl() {

    }
}
