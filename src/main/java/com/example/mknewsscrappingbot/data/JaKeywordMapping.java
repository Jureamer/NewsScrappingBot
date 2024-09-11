package com.example.mknewsscrappingbot.data;

import java.util.HashMap;

public class JaKeywordMapping implements IKeywordMapping {
    private static final String EN_NAME = "Jooang Illbo";
    private static final String KR_NAME = "중앙일보";
    private static final HashMap<String, String> keywordsMap = new HashMap<String, String>();

    static {
        keywordsMap.put("경제", "money");
        keywordsMap.put("사회", "society");
        keywordsMap.put("국제", "world");
        keywordsMap.put("정치", "politics");
        keywordsMap.put("문화", "culture");
        keywordsMap.put("스포츠", "sports");
        keywordsMap.put("라이프", "lifestyle");
        keywordsMap.put("피플", "people");
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
