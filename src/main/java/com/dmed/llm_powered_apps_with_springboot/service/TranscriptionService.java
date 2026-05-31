package com.dmed.llm_powered_apps_with_springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class TranscriptionService {

    private final TranscriptionModel transcriptionModel;
    private final TextToSpeechModel textToSpeechModel;

    public TranscriptionService(TranscriptionModel transcriptionModel, TextToSpeechModel textToSpeechModel) {
        this.transcriptionModel = transcriptionModel;
        this.textToSpeechModel = textToSpeechModel;
    }

    public String transcribeAudio(Resource audioFile) {
        AudioTranscriptionPrompt prompt = new AudioTranscriptionPrompt(
                audioFile);
        AudioTranscriptionResponse response = transcriptionModel.call(prompt);
        log.info("Transcribed audio: {}", response.getResult().getOutput());
        return response.getResult().getOutput();
    }

    public String textToAudio(String text) throws IOException {
        byte[] audioBytes = textToSpeechModel.call(String.valueOf(new TextToSpeechPrompt(text)));
        Path path = Paths.get("text-audio.mp3");
        Files.write(path, audioBytes);
        return "Audio file created at: " + path.toAbsolutePath();
    }
}