package com.dmed.llm_powered_apps_with_springboot.controller;

import com.dmed.llm_powered_apps_with_springboot.model.CountryLanguage;
import com.dmed.llm_powered_apps_with_springboot.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StructuredOutputChatController {
    private final ChatService chatService;

    public StructuredOutputChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat/structuredoutput")
    public ResponseEntity<List<CountryLanguage>> generalChat(@RequestParam String prompt) {
        List<CountryLanguage> countryLanguages = chatService.getGeneralChatResponse(prompt);
        return ResponseEntity.ok(countryLanguages);
    }

}