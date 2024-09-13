package com.example.mknewsscrappingbot.data.keywordMapping;

import java.util.HashMap;

public class CsKeywordMapping implements IKeywordMapping {
    private static final String EN_NAME = "The Chosun Ilbo";
    private static final String KR_NAME = "조선일보";
    private static final HashMap<String, String> keywordsMap = new HashMap<String, String>();

    static {
        keywordsMap.put("정치", "politics");
        keywordsMap.put("사회", "national");
        keywordsMap.put("국제", "international");
        keywordsMap.put("건강", "medical");
        keywordsMap.put("재테크", "investment");
        keywordsMap.put("스포츠", "sports");
        keywordsMap.put("문화연예", "culture-style");
        keywordsMap.put("쇼핑", "shopping");
    }

    public String getKeywordForCategory(String category) {
        return keywordsMap.getOrDefault(category, ""); // 기본값 설정 가능
    }


    @Override
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
