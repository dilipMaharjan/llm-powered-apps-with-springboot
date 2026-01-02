package com.dmed.llm_powered_apps_with_springboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@Slf4j
public class ChatConfig {

    @Value("classpath:airline_assistant_system_message.txt")
    Resource airlineAssistantSystemMessage;

    @Bean
    public ChatClient generalChatClient(ChatClient.Builder chatClientBuilder) {
        log.info("Configuring ChatClient bean for general assistant");
        chatClientBuilder.defaultSystem("You're a helpful assistant.");
        return chatClientBuilder.build();
    }

    @Bean
    public ChatClient airlineChatClient(ChatClient.Builder chatClientBuilder) {
        log.info("Configuring ChatClient bean for airline assistant");
        chatClientBuilder.defaultSystem(airlineAssistantSystemMessage);
        return chatClientBuilder.build();
    }
}
