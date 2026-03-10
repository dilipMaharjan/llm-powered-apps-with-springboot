package com.dmed.llm_powered_apps_with_springboot.config;

import com.dmed.llm_powered_apps_with_springboot.rag.postprocessor.KeywordPostProcessor;
import com.dmed.llm_powered_apps_with_springboot.rag.retriever.WebDocumentRetriever;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@Slf4j
public class ChatConfig {

    @Value("${keywords}")
    private String keywords;

    @Bean
    public ChatClient chatClientForRag(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        log.info("Configuring ChatClient bean for rag assistant");
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        return chatClientBuilder
                .defaultAdvisors(loggerAdvisor, memoryAdvisor)
                .build();
    }

    @Bean
    public ChatClient chatClientForRagWithAdvisor(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, RetrievalAugmentationAdvisor retrievalAugmentationAdvisor) {
        log.info("Configuring ChatClient bean for rag assistant with advisor");
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        return chatClientBuilder
                .defaultAdvisors(loggerAdvisor, memoryAdvisor, retrievalAugmentationAdvisor)
                .build();
    }

    @Bean
    RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(VectorStore vectorStore, ChatClient.Builder chatClientBuilder) {
        log.info("Configuring RetrievalAugmentationAdvisor bean for rag assistant");
        VectorStoreDocumentRetriever documentRetrieverConfig = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)
                .topK(3)
                .similarityThreshold(0.5)
                .build();
        return RetrievalAugmentationAdvisor.builder()
                .documentRetriever(documentRetrieverConfig)
                .documentPostProcessors(KeywordPostProcessor.builder(keywords))
                .build();
    }

    @Bean("webDocumentRetrievalChatClient")
    ChatClient webDocumentRetrieverChatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, RestClient.Builder restClientBuilder) {
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        log.info("Configuring webDocumentRetrieverChatClient bean for rag assistant");
        var webDocumentRetrieverAdvisor = RetrievalAugmentationAdvisor.builder()
                .queryTransformers(TranslationQueryTransformer
                        .builder()
                        .chatClientBuilder(chatClientBuilder
                                .clone())
                        .targetLanguage("English")
                        .build())
                .documentRetriever(WebDocumentRetriever.builder()
                        .restClientBuilder(restClientBuilder).maxResults(5).build())
                .build();
        return chatClientBuilder.defaultAdvisors(loggerAdvisor, memoryAdvisor, webDocumentRetrieverAdvisor)
                .build();
    }
}
