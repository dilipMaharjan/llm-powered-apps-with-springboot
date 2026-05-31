package com.dmed.llm_powered_apps_with_springboot.controller;

import com.dmed.llm_powered_apps_with_springboot.service.TranscriptionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AudioController {
    private final TranscriptionService transcriptionService;

    AudioController(TranscriptionService transcriptionService) {
        this.transcriptionService = transcriptionService;
    }

    @GetMapping("/audio/play-audio")
    public String audioToText(@Value("classpath:audio-text.mp3") Resource audioFile) {
        return transcriptionService.transcribeAudio(audioFile);
    }

    @GetMapping("/audio/text-to-audio")
    public String textToAudio(@RequestParam String text) throws Exception {
        return transcriptionService.textToAudio(text);
    }

}