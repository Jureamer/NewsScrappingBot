package com.example.mknewsscrappingbot.data.keywordMapping;

import java.util.HashMap;

public interface IKeywordMapping {
    String KR_NAME = "";
    String EN_NAME = "";
    HashMap<String, String> keywordsMap = new HashMap<String, String>();

    String getKeywordForCategory(String category);
    String toKeyString();
    String getKrName();
    String getEnName();
    default String[] getKeywordValues() {
        return keywordsMap.values().toArray(new String[0]);
    }
}
