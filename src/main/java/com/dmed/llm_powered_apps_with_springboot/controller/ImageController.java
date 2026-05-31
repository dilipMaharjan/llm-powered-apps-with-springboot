package com.dmed.llm_powered_apps_with_springboot.controller;

import com.dmed.llm_powered_apps_with_springboot.service.ImageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class ImageController {
    private final ImageService transcriptionService;

    ImageController(ImageService transcriptionService) {
        this.transcriptionService = transcriptionService;
    }


    @GetMapping("/image/text-to-image")
    public String textToAudio(@RequestParam String text) throws IOException {
        return transcriptionService.textToImage(text);
    }

}