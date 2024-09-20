package com.example.mknewsscrappingbot.data.newsSource;

import org.springframework.stereotype.Component;

@Component
public abstract class NewsSource {
    private final String requestUrl;
    private final String newsWrapCssSelector;
    private final String newsNodeCssSelector;
    private final String articleTitleCssSelector;
    private final String articleContentCssSelector;

    protected NewsSource(String requestUrl, String newsWrapCssSelector, String newsNodeCssSelector, String articleTitleCssSelector, String articleContentCssSelector) {
        this.requestUrl = requestUrl;
        this.newsWrapCssSelector = newsWrapCssSelector;
        this.newsNodeCssSelector = newsNodeCssSelector;
        this.articleTitleCssSelector = articleTitleCssSelector;
        this.articleContentCssSelector = articleContentCssSelector;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getNewsWrapCssSelector() {
        return newsWrapCssSelector;
    }

    public String getNewsNodeCssSelector() {
        return newsNodeCssSelector;
    }

    public String getArticleTitleCssSelector() {
        return articleTitleCssSelector;
    }

    public String getArticleContentCssSelector() {
        return articleContentCssSelector;
    }

    public String getCustomRequestUrl(String category) {
        return this.getRequestUrl() + category;
    };
}
