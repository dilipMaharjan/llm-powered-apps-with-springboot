package com.dmed.llm_powered_apps_with_springboot.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatConfig {

    @Autowired
    private JdbcChatMemoryRepository jdbcChatMemoryRepository;

    @Bean
    ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder().maxMessages(10).chatMemoryRepository(jdbcChatMemoryRepository).build();
    }

    @Bean
    public ChatClient generalChatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        chatClientBuilder.defaultSystem("You're a helpful assistant.");
        Advisor simpleLoggerAdvisor = new SimpleLoggerAdvisor();
        Advisor chatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        chatClientBuilder.defaultAdvisors(List.of(simpleLoggerAdvisor, chatMemoryAdvisor));
        return chatClientBuilder.build();
    }
}


