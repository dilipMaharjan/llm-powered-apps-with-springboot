package com.dmed.llm_powered_apps_with_springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;


@Slf4j
@Service
public class ImageService {

    private final ImageModel imageModel;

    public ImageService(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    public String textToImage(String text) throws IOException {
        var response = imageModel.call(new ImagePrompt(text));

        String base64Image = response.getResults().get(0).getOutput().getB64Json();
        // Decode Base64
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        Path path = Paths.get("text-to-image.png");

        // Save as PNG
        Files.write(path, imageBytes);
        return "Image generated at " + path.toAbsolutePath();
    }
}