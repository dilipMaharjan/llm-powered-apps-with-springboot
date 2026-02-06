package com.dmed.llm_powered_apps_with_springboot.controller;

import com.dmed.llm_powered_apps_with_springboot.service.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat/memory")
    public String chatMemory(@RequestParam String prompt) {
        return chatService.getChatMemoryChatResponse(prompt);
    }

    @GetMapping("/chat/memory-with-id")
    public String chatMemoryWithId(@RequestParam String prompt, @RequestParam String userId) {
        return chatService.getChatMemoryWithIdResponse(prompt, userId);
    }
}