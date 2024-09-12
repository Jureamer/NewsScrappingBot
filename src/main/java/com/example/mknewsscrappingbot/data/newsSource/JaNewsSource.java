package com.example.mknewsscrappingbot.data.newsSource;

public class JaNewsSource extends NewsSource {
    public JaNewsSource() {
        super("https://www.joongang.co.kr/",
    "section.chain_wrap",
    "h2.headline",
    "h1.headline",
"#article_body.article_body.fs3"
        );
    }

    @Override
    public void customizeRequestUrl() {

    }
}
