package com.example.mknewsscrappingbot.data.keywordMapping;

import java.util.HashMap;

public class DaKeywordMapping extends AbstractKeywordMapping {
    private static final String EN_NAME = "Dong-A Ilbo";
    private static final String KR_NAME = "동아일보";
    private static final HashMap<String, String> keywordsMap = new HashMap<String, String>();

    public DaKeywordMapping() {
        keywordsMap.put("정치", "Politics");
        keywordsMap.put("경제", "Economy");
        keywordsMap.put("사회", "Society");
        keywordsMap.put("국제", "Inter");
        keywordsMap.put("문화", "Culture");
        keywordsMap.put("연예", "Entertainment");
        keywordsMap.put("스포츠", "Sports");
        keywordsMap.put("헬스", "Health");
    }
}
