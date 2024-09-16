package com.example.mknewsscrappingbot.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LangchaingConfig {
    @Value("${ollama.model.name}")
    private String modelName;

    @Value("${ollama.host.url}")
    private String baseUrl;

    @Bean
    ChatLanguageModel chatLanguageModel(){
        return OllamaChatModel.builder()
                .modelName(modelName)
                .baseUrl(baseUrl)
                .build();
    }
}