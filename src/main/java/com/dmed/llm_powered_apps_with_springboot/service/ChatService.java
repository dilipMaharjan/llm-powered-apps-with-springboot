package com.dmed.llm_powered_apps_with_springboot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String getChartResponse(String prompt) {
        return Objects.requireNonNull(chatClient.
                        prompt(prompt)
                        .call()
                        .chatResponse())
                .getResult()
                .getOutput()
                .getText();
    }
}