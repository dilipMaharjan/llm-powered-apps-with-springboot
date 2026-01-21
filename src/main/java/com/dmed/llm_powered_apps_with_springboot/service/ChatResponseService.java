package com.dmed.llm_powered_apps_with_springboot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ChatResponseService {

    private final ChatClient generalChatClient;

    public ChatResponseService(@Qualifier("generalChatClient") ChatClient generalChatClient) {
        this.generalChatClient = generalChatClient;
    }

    public ChatResponse getGeneralChatResponse(String prompt) {
        return Objects.requireNonNull(generalChatClient.
                prompt(prompt)
                .call()
                .chatResponse());
    }

    public ChatClientResponse getGeneralChatClientResponse(String prompt) {
        ChatOptions chatOptions = ChatOptions.builder()
//                .model("llama3.2")
                // Deterministic responses
//                .temperature(0.0)
                // More creative responses
                .temperature(0.7)
                .build();
        return Objects.requireNonNull(generalChatClient.
                prompt(prompt)
                .options(chatOptions)
                .call()
                .chatClientResponse());
    }
}