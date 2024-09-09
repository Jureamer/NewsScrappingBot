package com.example.mknewsscrappingbot.data;

import java.util.HashMap;

public class KeywordMapping {
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


    public static String getKeywordForCategory(String category) {
        return keywordsMap.getOrDefault(category, ""); // 기본값 설정 가능
    }
}
