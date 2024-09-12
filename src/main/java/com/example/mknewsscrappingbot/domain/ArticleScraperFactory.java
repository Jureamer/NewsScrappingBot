package com.example.mknewsscrappingbot.domain;

import com.example.mknewsscrappingbot.data.newsSource.*;
import org.springframework.stereotype.Component;

@Component
public class ArticleScraperFactory {
    private final MkArticleScraper mkArticleScraper;
    private final CsArticleScraper csArticleScraper;
    private final JaArticleScraper jaArticleScraper;
    private final DaArticleScraper daArticleScraper;
    private final HkArticleScraper hkArticleScraper;

    public ArticleScraperFactory() {
        this.mkArticleScraper = new MkArticleScraper(new MkNewsSource());
        this.csArticleScraper = new CsArticleScraper(new CsNewsSource());
        this.jaArticleScraper = new JaArticleScraper(new JaNewsSource());
        this.daArticleScraper = new DaArticleScraper(new DaNewsSource());
        this.hkArticleScraper = new HkArticleScraper(new HkNewsSource());
    }

    public ArticleScraper getArticleScraper(String media) {
        return switch (media) {
            case "MK" -> mkArticleScraper;
            case "HK" -> hkArticleScraper;
            case "CS" -> csArticleScraper;
            case "JA" -> jaArticleScraper;
            case "DA" -> daArticleScraper;
            default -> throw new IllegalArgumentException("지원하지 않는 미디어입니다: " + media);
        };
    }
}
