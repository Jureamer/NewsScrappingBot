package com.example.mknewsscrappingbot.domain;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ChatService {
    private static final String MODEL = "llama3.1";
    private static final String BASE_URL = "http://localhost:11434";
    private static Duration timeout = Duration.ofSeconds(120);

    static String basicModel(String question) {
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl(BASE_URL)
                .modelName(MODEL)
                .timeout(timeout)
                .build();

        String rolePromptTemplate = "너는 나의 보조 기자야. 역할은 주어진 기사를 3줄 요약해야 돼.";
        String inputPromptTemplate = rolePromptTemplate + "\n\n해당 텍스트를 3줄 요약해줘:\n" + question;

        String answer = model.generate(inputPromptTemplate);
        System.out.println(answer);
        return answer;
    }
}