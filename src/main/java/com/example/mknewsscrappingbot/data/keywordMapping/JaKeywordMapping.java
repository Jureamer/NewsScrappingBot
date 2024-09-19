package com.example.mknewsscrappingbot.data.keywordMapping;

import java.util.HashMap;

public class JaKeywordMapping extends AbstractKeywordMapping {
    private static final String EN_NAME = "Jooang Ilbo";
    private static final String KR_NAME = "중앙일보";
    private static final HashMap<String, String> keywordsMap = new HashMap<String, String>();

    public JaKeywordMapping() {
        keywordsMap.put("경제", "money");
        keywordsMap.put("사회", "society");
        keywordsMap.put("국제", "world");
        keywordsMap.put("정치", "politics");
        keywordsMap.put("문화", "culture");
        keywordsMap.put("스포츠", "sports");
        keywordsMap.put("라이프", "lifestyle");
        keywordsMap.put("피플", "people");
    }
}
