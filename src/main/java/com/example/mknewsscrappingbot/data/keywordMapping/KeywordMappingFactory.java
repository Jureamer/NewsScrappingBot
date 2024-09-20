package com.example.mknewsscrappingbot.data.keywordMapping;

import com.example.mknewsscrappingbot.data.newsSource.*;
import org.springframework.stereotype.Component;

@Component
public class KeywordMappingFactory {
    public IKeywordMapping getKeywordMapping(String newsName) {
        switch (newsName) {
            case "HK":
                return new HkKeywordMapping();
            case "MK":
                return new MkKeywordMapping();
            case "CS":
                return new CsKeywordMapping();
            case "JA":
                return new JaKeywordMapping();
            case "DA":
                return new DaKeywordMapping();
            default:
                throw new IllegalArgumentException("Invalid news source: " + newsName);
        }
    }
}
