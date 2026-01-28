package com.dmed.llm_powered_apps_with_springboot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Objects;

@Service
public class ChatService {

    private final ChatClient generalChatClient;

    public ChatService(@Qualifier("generalChatClient") ChatClient generalChatClient) {
        this.generalChatClient = generalChatClient;
    }


    public Flux<String> getStreamChatResponse(String prompt) {
        return Objects.requireNonNull(generalChatClient.
                prompt(prompt)
                .stream()
                .content());
    }
}