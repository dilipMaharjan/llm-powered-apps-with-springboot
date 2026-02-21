package com.dmed.llm_powered_apps_with_springboot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ChatService {

    private final ChatClient generalChatClient;


    public ChatService(ChatClient generalChatClient) {
        this.generalChatClient = generalChatClient;
    }

    public String getGeneralChatResponse(String prompt) {
        return Objects.requireNonNull(generalChatClient.
                        prompt(prompt)
                        .call()
                        .chatResponse())
                .getResult()
                .getOutput()
                .getText();
    }
}