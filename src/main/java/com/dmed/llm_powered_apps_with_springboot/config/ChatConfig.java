package com.dmed.llm_powered_apps_with_springboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ChatConfig {

    @Bean
    public ChatClient chatClientForRag(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        log.info("Configuring ChatClient bean for rag assistant");
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        return chatClientBuilder
                .defaultAdvisors(loggerAdvisor, memoryAdvisor)
                .build();
    }
}
