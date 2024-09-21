package com.example.mknewsscrappingbot.news.source;

import org.springframework.stereotype.Component;

@Component
public class NewsSourceFactory {
    public NewsSource getNewsSource(String newsName) {
        switch (newsName) {
            case "HK":
                return new HkNewsSource();
            case "MK":
                return new MkNewsSource();
            case "CS":
                return new CsNewsSource();
            case "JA":
                return new JaNewsSource();
            case "DA":
                return new DaNewsSource();
            default:
                throw new IllegalArgumentException("Invalid news source: " + newsName);
        }
    }
}
