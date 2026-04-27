package com.dmed.llm_powered_apps_with_springboot.rag;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DataLoader {
    private final VectorStore vectorStore;

    // Load the PDF file as a resource
    @Value("classpath:pdf/code-of-conduct.pdf")
    private Resource codeOfConduct;

    public DataLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadText() {
        log.info("Loading text during bean initialization...");
        List<String> data = List.of(
                "Spring Boot is a powerful framework for building Java applications.",
                "RAG stands for Retrieval-Augmented Generation, a technique in natural language processing.",
                "Vector stores are used to store and retrieve high-dimensional data efficiently.",
                "LLM stands for Large Language Model, which is a type of artificial intelligence model designed to understand and generate human language.",
                "Spring Boot simplifies the development of Java applications by providing a set of tools and conventions.",
                "RAG combines retrieval of relevant information with generation of new content, enhancing the capabilities of language models.",
                "Vector stores can be used in various applications, including recommendation systems and natural language processing.",
                "LLMs have revolutionized the field of natural language processing, enabling applications such as chatbots, language translation, and content generation.",
                "Spring Boot allows developers to create stand-alone, production-grade applications with minimal configuration.",
                "RAG can be used to improve the performance of language models by providing them with relevant");

        List<Document> documents = data.stream()
                .map(Document::new)
                .toList();
        vectorStore.add(documents);
    }

    @PostConstruct
    public void loadPdf() {
        log.info("Loading PDF during bean initialization...");
        //read the PDF file using TikaDocumentReader
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(codeOfConduct);
        List<Document> documents = tikaDocumentReader.get();
        //Configure parameters for the text splitter
        TextSplitter textSplitter = TokenTextSplitter.builder()
                .withChunkSize(100)
                .withMaxNumChunks(400)
                .build();

        //Split the documents into smaller chunks
        List<Document> splitDocuments = textSplitter.split(documents);

        //add them to the vector store
        vectorStore.add(splitDocuments);
    }
}
