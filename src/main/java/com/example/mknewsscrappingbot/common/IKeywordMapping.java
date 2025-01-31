package com.example.mknewsscrappingbot.common;

import java.util.Map;

public interface IKeywordMapping {
    String getKeywordForCategory(String category);
    String toKeyString();
    String getKrName();
    String getEnName();
    String[] getKeywordValues();

    Map.Entry<String, String>[] getEntryArray();
}
