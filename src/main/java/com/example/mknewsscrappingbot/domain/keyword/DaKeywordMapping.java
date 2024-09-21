package com.example.mknewsscrappingbot.domain.keyword;

import org.springframework.stereotype.Component;

@Component
public class DaKeywordMapping extends AbstractKeywordMapping {
    public DaKeywordMapping() {
        super();
        this.KR_NAME = "동아일보";
        this.EN_NAME = "Dong-A Ilbo";
        keywordsMap.put("정치", "Politics");
        keywordsMap.put("경제", "Economy");
        keywordsMap.put("사회", "Society");
        keywordsMap.put("국제", "Inter");
        keywordsMap.put("문화", "Culture");
        keywordsMap.put("연예", "Entertainment");
        keywordsMap.put("스포츠", "Sports");
        keywordsMap.put("헬스", "Health");
    }
}
