package com.example.mknewsscrappingbot.data.keywordMapping;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CsKeywordMapping extends AbstractKeywordMapping {

    public CsKeywordMapping() {
        super();
        this.KR_NAME = "조선일보";
        this.EN_NAME = "The Chosun Ilbo";
        keywordsMap.put("정치", "politics");
        keywordsMap.put("사회", "national");
        keywordsMap.put("국제", "international");
        keywordsMap.put("건강", "medical");
//        keywordsMap.put("재테크", "investment"); // 재테크의 경우 Top News 미존재
        keywordsMap.put("스포츠", "sports");
        keywordsMap.put("문화", "culture-style");
        keywordsMap.put("연예", "culture-style");
        keywordsMap.put("쇼핑", "shopping");
    }
}
