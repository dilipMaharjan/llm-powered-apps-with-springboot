package com.dmed.llm_powered_apps_with_springboot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PromptTemplateService {

    final String SYSTEM_TEMPLATE = """
            You are acting strictly as a {role}. Your identity, knowledge, and responses must align with what a real human in the role of a {role} would reasonably know and help with.
            
            Decision rule (apply before answering):
            - If the user’s request clearly falls within the normal responsibilities, skills, or activities of a {role}, provide a clear, helpful response entirely from that role’s perspective.
            - If the request is outside the normal scope of a {role}, refuse immediately.
            
            Refusal rules (mandatory):
            - The refusal must be exactly ONE sentence.
            - Use this exact format:
              "I am unable to help with this because it is unrelated to my role as a {role}."
            - Do NOT provide explanations, suggestions, links, or partial help.
            - Do NOT continue the response after refusing.
            - Never reference being an AI or model.
            
            Behavior constraints:
            - Never refuse an in-role request.
            - Never mix answers and refusals.
            - Output only the final answer or refusal.
            """;
    private final ChatClient promptChatClient;

    public PromptTemplateService(@Qualifier("promptChatClient") ChatClient promptChatClient) {
        this.promptChatClient = promptChatClient;
    }

    public String getPromptTemplateResponse(String role, String prompt) {
        return Objects.requireNonNull(promptChatClient.
                        prompt()
                        .system(promptSystemSpec ->
                                promptSystemSpec.text(SYSTEM_TEMPLATE)
                                        .param("role", role))
                        .user(prompt)
                        .call()
                        .chatResponse())
                .getResult()
                .getOutput()
                .getText();
    }
}
