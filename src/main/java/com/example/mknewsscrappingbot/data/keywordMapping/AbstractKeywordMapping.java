package com.example.mknewsscrappingbot.data.keywordMapping;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AbstractKeywordMapping implements IKeywordMapping {
    protected final String KR_NAME = "";
    protected final String EN_NAME = "";
    protected final Map<String, String> keywordsMap = new HashMap<>();

    @Override
    public String getKrName() {
        return KR_NAME;
    };
    @Override
    public String getEnName() {
        return EN_NAME;
    };

    public String getKeywordForCategory(String category) {
        return keywordsMap.getOrDefault(category, "");
    }

    @Override
    public String toKeyString() {
        return keywordsMap.keySet().toString();
    }

    @Override
    public String[] getKeywordValues() {
        return keywordsMap.values().toArray(new String[0]);
    }
}
