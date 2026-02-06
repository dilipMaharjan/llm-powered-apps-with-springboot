package com.dmed.llm_powered_apps_with_springboot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@Service
public class ChatService {

    private final ChatClient generalChatClient;

    public ChatService(ChatClient generalChatClient) {
        this.generalChatClient = generalChatClient;
    }

    public String getChatMemoryChatResponse(String prompt) {
        return Objects.requireNonNull(generalChatClient.
                        prompt(prompt)
                        .call()
                        .chatResponse())
                .getResult()
                .getOutput()
                .getText();
    }

    public String getChatMemoryWithIdResponse(String prompt, String userId) {
        return Objects.requireNonNull(generalChatClient.
                prompt().user(prompt).advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, userId)).call().content());
    }
}