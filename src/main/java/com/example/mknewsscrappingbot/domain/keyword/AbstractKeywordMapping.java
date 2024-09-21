package com.example.mknewsscrappingbot.domain.keyword;

import com.example.mknewsscrappingbot.common.IKeywordMapping;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AbstractKeywordMapping implements IKeywordMapping {
    protected String KR_NAME;
    protected String EN_NAME;
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

    @Override
    public Map.Entry<String, String>[] getEntryArray() {
        Map.Entry<String, String>[] entryArray = keywordsMap.entrySet().toArray(new Map.Entry[0]);
        return entryArray;
    };
}
