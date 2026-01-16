package com.dmed.llm_powered_apps_with_springboot.controller;

import com.dmed.llm_powered_apps_with_springboot.service.ChatResponseService;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class ChatResponseController {
    private final ChatResponseService chatResponseService;

    public ChatResponseController(ChatResponseService chatResponseService) {
        this.chatResponseService = chatResponseService;
    }

    @GetMapping("/chat-client-response/{prompt}")
    public ChatClientResponse generalChatClient(@PathVariable String prompt) {
        return chatResponseService.getGeneralChatClientResponse(prompt);
    }

    @GetMapping("/chat-response/{prompt}")
    public ChatResponse generalChat(@PathVariable String prompt) {
        return chatResponseService.getGeneralChatResponse(prompt);
    }

}