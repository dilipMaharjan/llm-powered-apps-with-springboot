package com.dmed.llm_powered_apps_with_springboot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.model.ChatResponse;
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
        return Objects.requireNonNull(generalChatClient.
                prompt(prompt)
                .call()
                .chatClientResponse());
    }
}