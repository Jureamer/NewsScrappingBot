package com.example.mknewsscrappingbot.data.newsSource;

public class NewsSourceFactory {
    public NewsSource createNewsSource(String newsSource) {
        switch (newsSource) {
            case "hk":
                return new HkNewsSource();
            case "da":
                return new DaNewsSource();
            case "cs":
                return new CsNewsSource();
            default:
                throw new IllegalArgumentException("Invalid news source: " + newsSource);
        }
    }
}
