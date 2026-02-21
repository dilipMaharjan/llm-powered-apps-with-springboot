package com.dmed.llm_powered_apps_with_springboot.controller;

import com.dmed.llm_powered_apps_with_springboot.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RagController {
    private final ChatService chatService;

    public RagController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/rag")
    public ResponseEntity<String> ragResponse(@RequestParam String prompt, @RequestHeader String username) {
        return ResponseEntity.ok(chatService.getChatResponseFromRag(prompt, username));
    }
}