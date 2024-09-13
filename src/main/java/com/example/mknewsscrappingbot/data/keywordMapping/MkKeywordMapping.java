package com.example.mknewsscrappingbot.data.keywordMapping;

import java.util.HashMap;

public class MkKeywordMapping implements IKeywordMapping {
    private static final String EN_NAME = "Maeil Business Newspaper";
    private static final String KR_NAME = "매일경제신문";
    private static final HashMap<String, String> keywordsMap = new HashMap<String, String>();

    static {
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

    public String getKeywordForCategory(String category) {
        return keywordsMap.getOrDefault(category, ""); // 기본값 설정 가능
    }

    public String toKeyString() {
        return keywordsMap.keySet().toString();
    }

    @Override
    public String getKrName() {
        return KR_NAME;
    }

    @Override
    public String getEnName() {
        return EN_NAME;
    }
}
