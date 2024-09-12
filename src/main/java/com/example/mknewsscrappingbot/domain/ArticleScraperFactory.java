package com.example.mknewsscrappingbot.domain;

import org.springframework.stereotype.Component;

@Component
public class ArticleScraperFactory {
    private final MkArticleScraper mkArticleScraper;
    private final CsArticleScraper csArticleScraper;
    private final JaArticleScraper jaArticleScraper;
    private final DaArticleScraper daArticleScraper;
    private final HkArticleScraper hkArticleScraper;

    public ArticleScraperFactory(
            MkArticleScraper mkArticleScraper,
            CsArticleScraper csArticleScraper,
            JaArticleScraper jaArticleScraper,
            DaArticleScraper daArticleScraper,
            HkArticleScraper hkArticle) {
        this.mkArticleScraper = mkArticleScraper;
        this.csArticleScraper = csArticleScraper;
        this.jaArticleScraper = jaArticleScraper;
        this.daArticleScraper = daArticleScraper;
        this.hkArticleScraper = hkArticle;
    }

    public ArticleScraper getArticleScraper(String media) {
        switch (media) {
            case "MK":
                return mkArticleScraper;
            case "HK":
                return hkArticleScraper;
            case "CS":
                return csArticleScraper;
            case "JA":
                return jaArticleScraper;
            case "DA":
                return daArticleScraper;
            default:
                throw new IllegalArgumentException("지원하지 않는 미디어입니다: " + media);
        }
    }
}
