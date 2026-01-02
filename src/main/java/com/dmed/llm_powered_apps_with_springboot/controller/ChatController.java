package com.dmed.llm_powered_apps_with_springboot.controller;

import com.dmed.llm_powered_apps_with_springboot.service.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat/{prompt}")
    public String generalChat(@PathVariable String prompt) {
        return chatService.getGeneralChatResponse(prompt);
    }

    @GetMapping("/chat/airline/{prompt}")
    public String airlineChat(@PathVariable String prompt) {
        return chatService.getAirlineChatResponse(prompt);
    }
}