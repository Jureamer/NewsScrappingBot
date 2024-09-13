package com.example.mknewsscrappingbot.data.keywordMapping;

import java.util.HashMap;

public class HkKeywordMapping implements IKeywordMapping {
    private static final String EN_NAME = "The Korea Economic Daily";
    private static final String KR_NAME = "한국경제신문";
    private static final HashMap<String, String> keywordsMap = new HashMap<String, String>();

    static {
        keywordsMap.put("오피니언", "all-news-opinion");
        keywordsMap.put("경제", "all-news-economy");
        keywordsMap.put("정치", "all-news-politics");
        keywordsMap.put("사회", "all-news-society");
        keywordsMap.put("증권", "all-news-finance");
        keywordsMap.put("부동산", "all-news-realestate");
        keywordsMap.put("국제", "all-news-international");
        keywordsMap.put("IT", "all-news-it");
        keywordsMap.put("생활문화", "all-news-life");
        keywordsMap.put("스포츠", "all-news-sports");
        keywordsMap.put("연예", "all-news-entertainment");
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
