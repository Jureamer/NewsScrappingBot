package com.example.mknewsscrappingbot.service.chat;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ChatService {
    private static final String MODEL = "llama3.1";
    private static final String BASE_URL = "http://localhost:11434";
    private static final Duration timeout = Duration.ofSeconds(120);
    private static final int MAX_DESCRIPTION_LENGTH = 4096;

    static ChatLanguageModel model = OllamaChatModel.builder()
            .baseUrl(BASE_URL)
            .modelName(MODEL)
            .timeout(timeout)
            .build();

    public String getSummary(String question) {
        String promptSummary = getRolePrompt(question);

        while (promptSummary.length() > MAX_DESCRIPTION_LENGTH) {
            promptSummary = model.generate(promptSummary);
        }

        return promptSummary;
    }

    private String getRolePrompt(String question) {
        String rolePromptTemplate = "너는 나의 보조 기자야. 역할은 주어진 기사를 3줄로 요약하는 것이야.";
        String inputPromptTemplate = rolePromptTemplate + "\n\n다음 텍스트를 다음과 같은 형식으로 3줄 요약해줘. 대답 하지말고 요약만 해줘:\n" +
                "1. 내용\n" +
                "2. 내용\n" +
                "3. 내용\n\n" +
                "텍스트: \n" + question;
        return model.generate(inputPromptTemplate);
    }
}