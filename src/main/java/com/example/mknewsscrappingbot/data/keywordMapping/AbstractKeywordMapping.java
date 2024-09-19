package com.example.mknewsscrappingbot.data.keywordMapping;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractKeywordMapping implements IKeywordMapping {
    protected final Map<String, String> keywordsMap = new HashMap<>();
    protected String KR_NAME;
    protected String EN_NAME;

    @Override
    public String getKeywordForCategory(String category) {
        return keywordsMap.getOrDefault(category, "");
    }

    @Override
    public String toKeyString() {
        return String.join(", ", keywordsMap.keySet());
    }

    @Override
    public String[] getKeywordValues() {
        return keywordsMap.values().toArray(new String[0]);
    }

    // 추상 메소드: 구체 클래스에서 구현하도록 강제
    @Override
    public String getKrName() {
        return KR_NAME;
    };

    @Override
    public String getEnName() {
        return EN_NAME;
    };
}
