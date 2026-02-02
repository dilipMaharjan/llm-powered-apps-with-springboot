package com.dmed.llm_powered_apps_with_springboot.service;

import com.dmed.llm_powered_apps_with_springboot.model.CountryLanguage;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ChatService {

    private final ChatClient generalChatClient;


    public ChatService(ChatClient generalChatClient) {
        this.generalChatClient = generalChatClient;
    }

    public List<CountryLanguage> getGeneralChatResponse(String prompt) {
        return Objects.requireNonNull(
                generalChatClient
                        .prompt(prompt)
                        .call()
                        .entity(new ParameterizedTypeReference<List<CountryLanguage>>() {
                        })
        );
    }

    public List<String> getStringList(String prompt) {
        return Objects.requireNonNull(
                generalChatClient
                        .prompt(prompt)
                        .call()
                        .entity(new ListOutputConverter()));
    }

    public Map<String, Object> getMap(String prompt) {
        return Objects.requireNonNull(
                generalChatClient
                        .prompt(prompt)
                        .call()
                        .entity(new MapOutputConverter()));
    }
}