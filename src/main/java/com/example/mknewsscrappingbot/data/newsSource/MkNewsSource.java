package com.example.mknewsscrappingbot.data.newsSource;

public class MkNewsSource extends NewsSource {
    public MkNewsSource() {
        super(
                "https://www.mk.co.kr/",
                "//*[@id='container']/section/div[2]/section/div/div/div/h2",
                "//*[@id='container']/section/div[3]/section/div[1]/div[1]/div[1]",
                ".best_view_news_wrap",
                "li.news_node"
        );
    }

    @Override
    public void customizeRequestUrl() {

    }
}
