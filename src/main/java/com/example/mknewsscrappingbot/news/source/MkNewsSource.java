package com.example.mknewsscrappingbot.news.source;

import org.springframework.stereotype.Component;

@Component
public class MkNewsSource extends NewsSource {
    public MkNewsSource() {
        super(
                "https://www.mk.co.kr/",
                ".best_view_news_wrap",
                ".news_node",
                ".txt_area > .news_ttl",
                ".news_cnt_detail_wrap"
        );
    }

    @Override
    public String getCustomRequestUrl(String category) {
        return this.getRequestUrl() + "news/" + category;
    }
}
