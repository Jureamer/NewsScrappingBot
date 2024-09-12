package com.example.mknewsscrappingbot.data.newsSource;

public class DaNewsSource extends NewsSource {
    public DaNewsSource() {
        super("https://www.donga.com/",
                "div.sec_body",
                "li",
                "section.head_group h1",
                "section.news_view");
    }

    @Override
    public void customizeRequestUrl() {

    }
}
