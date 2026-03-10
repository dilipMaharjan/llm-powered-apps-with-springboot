package com.dmed.llm_powered_apps_with_springboot.rag.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.postretrieval.document.DocumentPostProcessor;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class KeywordPostProcessor implements DocumentPostProcessor {

    private final Set<String> keywords;
    private final Pattern keywordPattern;

    private KeywordPostProcessor(String keywordCsv) {

        Assert.hasText(keywordCsv, "keywords cannot be empty");

        this.keywords = Arrays.stream(keywordCsv.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        String regex = "\\b(" + String.join("|", this.keywords) + ")\\b";
        this.keywordPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    public static KeywordPostProcessor builder(String keywordCsv) {
        return new KeywordPostProcessor(keywordCsv);
    }

    @Override
    public List<Document> process(Query query, List<Document> documents) {

        if (CollectionUtils.isEmpty(documents)) {
            return documents;
        }

        return documents.stream()
                .map(document -> {
                    String text = document.getText() != null ? document.getText() : "";
                    String highlighted = highlightKeywords(text);

                    return document.mutate()
                            .text(highlighted)
                            .metadata("keywords_highlighted", true)
                            .build();
                })
                .toList();
    }

    private String highlightKeywords(String text) {
        return keywordPattern.matcher(text)
                .replaceAll(match -> "****");
    }
}