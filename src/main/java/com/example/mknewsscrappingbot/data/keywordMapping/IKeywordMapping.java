package com.example.mknewsscrappingbot.data.keywordMapping;

public interface IKeywordMapping {
    String getKeywordForCategory(String category);
    String toKeyString();
    String getKrName();
    String getEnName();
    String[] getKeywordValues();
}
