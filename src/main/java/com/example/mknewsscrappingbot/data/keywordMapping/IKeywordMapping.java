package com.example.mknewsscrappingbot.data.keywordMapping;

import java.util.HashMap;

public interface IKeywordMapping {
    String getKeywordForCategory(String category);
    String toKeyString();
    String getKrName();
    String getEnName();
    String[] getKeywordValues();
}
