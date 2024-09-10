package com.example.mknewsscrappingbot.data;

public interface IKeywordMapping {
    String KR_NAME = "";
    String EN_NAME = "";

    String getKeywordForCategory(String category);
    String toKeyString();
    String getKrName();
    String getEnName();
}
