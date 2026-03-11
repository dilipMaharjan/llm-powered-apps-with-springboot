package com.dmed.llm_powered_apps_with_springboot.config;

import com.dmed.llm_powered_apps_with_springboot.tool.TimeTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ChatConfig {

    @Bean
    public ChatClient generalChatClient(ChatClient.Builder chatClientBuilder, TimeTool timeTool) {
        log.info("Configuring ChatClient bean for general assistant");
        chatClientBuilder.defaultSystem("""
                You are a helpful assistant.
                """).build();
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        return chatClientBuilder
                .defaultTools(timeTool)
                .defaultAdvisors(loggerAdvisor)
                .build();
    }
}
