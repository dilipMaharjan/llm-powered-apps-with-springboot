package com.dmed.llm_powered_apps_with_springboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ChatConfig {

    @Bean
    public ChatClient generalChatClient(ChatClient.Builder chatClientBuilder) {
        log.info("Configuring ChatClient bean for general assistant");
        chatClientBuilder.defaultSystem("You're a helpful assistant.");
        return chatClientBuilder.build();
    }
}
