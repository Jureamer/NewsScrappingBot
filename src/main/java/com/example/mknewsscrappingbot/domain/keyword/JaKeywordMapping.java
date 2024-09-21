package com.example.mknewsscrappingbot.domain.keyword;

import org.springframework.stereotype.Component;

@Component
public class JaKeywordMapping extends AbstractKeywordMapping {

    public JaKeywordMapping() {
        super();
        this.KR_NAME = "중앙일보";
        this.EN_NAME = "Jooang Ilbo";
        keywordsMap.put("경제", "money");
        keywordsMap.put("사회", "society");
        keywordsMap.put("국제", "world");
        keywordsMap.put("정치", "politics");
        keywordsMap.put("문화", "culture");
        keywordsMap.put("스포츠", "sports");
        keywordsMap.put("라이프", "lifestyle");
        keywordsMap.put("피플", "people");
    }
}
