package com.dmed.llm_powered_apps_with_springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@Service
@Slf4j
public class ChatService {

    private final ChatClient chatClientForRag;

    private final ChatClient chatClientForRagWithAdvisor;
    private final VectorStore vectorStore;
    @Value("classpath:promptTemplates/systemPromptTemplateForRag.st")
    private Resource promptTemplateForRag;


    public ChatService(@Qualifier("chatClientForRag") ChatClient chatClientForRag, @Qualifier("chatClientForRagWithAdvisor") ChatClient chatClientForRagWithAdvisor, ChatMemory chatMemory, VectorStore vectorStore) {
        this.chatClientForRag = chatClientForRag;
        this.chatClientForRagWithAdvisor = chatClientForRagWithAdvisor;
        this.vectorStore = vectorStore;
    }

    public String getChatResponseFromRag(String prompt, String username) {
        //Search for relevant top 3 documents in the vector store based on the prompt
        SearchRequest searchRequest = SearchRequest.builder()
                .query(prompt)
                .topK(3)
                .similarityThreshold(0.5)
                .build();
        List<Document> top3SearchResult = vectorStore.similaritySearch(searchRequest);
        log.info("Top 3 search result: {}", top3SearchResult);
        //Extract the text from the retrieved documents and concatenate them into a single string
        String mappedSearchResult = top3SearchResult.stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));
        //Combine the retrieved information with the original prompt to create a new prompt for the chat client
        return chatClientForRag.prompt(prompt)
                .system(
                        promptSystemSpec -> promptSystemSpec.text(promptTemplateForRag)
                                .param("documents", mappedSearchResult)
                                .param("question", prompt)
                )
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, username))
                .user(prompt)
                .call()
                .content();
    }

    public String getChatResponseFromRagWithRetriverAdvisor(String prompt, String username) {
        return chatClientForRagWithAdvisor.prompt(prompt)
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, username))
                .user(prompt)
                .call()
                .content();
    }
}