package com.example.mknewsscrappingbot.data.keywordMapping;

import java.util.HashMap;

public class CsKeywordMapping extends AbstractKeywordMapping {
    private static final String EN_NAME = "The Chosun Ilbo";
    private static final String KR_NAME = "조선일보";
    private static final HashMap<String, String> keywordsMap = new HashMap<String, String>();

    public CsKeywordMapping() {
        keywordsMap.put("정치", "politics");
        keywordsMap.put("사회", "national");
        keywordsMap.put("국제", "international");
        keywordsMap.put("건강", "medical");
        keywordsMap.put("재테크", "investment");
        keywordsMap.put("스포츠", "sports");
        keywordsMap.put("문화연예", "culture-style");
        keywordsMap.put("쇼핑", "shopping");
    }
}
