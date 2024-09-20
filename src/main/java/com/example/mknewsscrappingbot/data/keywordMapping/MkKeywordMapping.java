package com.example.mknewsscrappingbot.data.keywordMapping;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class MkKeywordMapping extends AbstractKeywordMapping {
    private static final String EN_NAME = "Maeil Business Newspaper";
    private static final String KR_NAME = "매일경제신문";

    public MkKeywordMapping() {
        super();
        keywordsMap.put("경제", "economy");
        keywordsMap.put("비즈니스", "Business");
        keywordsMap.put("사회", "society");
        keywordsMap.put("세계", "world");
        keywordsMap.put("부동산", "realestate");
        keywordsMap.put("주식", "stock");
        keywordsMap.put("IT", "it");
        keywordsMap.put("정치", "politics");
        keywordsMap.put("문화", "culture");
        keywordsMap.put("스포츠", "sports");
    }
}
