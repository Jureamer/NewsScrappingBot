package com.example.mknewsscrappingbot.data;

import java.util.HashMap;

public class DaKeywordMapping implements IKeywordMapping {
    private static final String EN_NAME = "Dong-A Ilbo";
    private static final String KR_NAME = "동아일보";
    private static final HashMap<String, String> keywordsMap = new HashMap<String, String>();

    static {
        keywordsMap.put("정치", "Politics");
        keywordsMap.put("경제", "Economy");
        keywordsMap.put("사회", "Society");
        keywordsMap.put("국제", "Inter");
        keywordsMap.put("문화", "Culture");
        keywordsMap.put("연예", "Entertainment");
        keywordsMap.put("스포츠", "Sports");
        keywordsMap.put("헬스", "Health");
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
