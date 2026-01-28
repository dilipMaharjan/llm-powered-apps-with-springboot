package com.dmed.llm_powered_apps_with_springboot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Objects;

@Service
public class ChatService {

    private final ChatClient generalChatClient;

    private final ChatClient airlineChatClient;

    public ChatService(@Qualifier("generalChatClient") ChatClient generalChatClient,
                       @Qualifier("airlineChatClient") ChatClient airlineChatClient) {
        this.generalChatClient = generalChatClient;
        this.airlineChatClient = airlineChatClient;
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

    public Flux<String> getStreamChatResponse(String prompt) {
        return Objects.requireNonNull(generalChatClient.
                prompt(prompt)
                .stream()
                .content());
    }

    public String getAirlineChatResponse(String prompt) {
        return Objects.requireNonNull(airlineChatClient.
                        prompt(prompt)
                        .call()
                        .chatResponse())
                .getResult()
                .getOutput()
                .getText();
    }
}