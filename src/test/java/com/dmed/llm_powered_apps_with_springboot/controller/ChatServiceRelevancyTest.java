package com.dmed.llm_powered_apps_with_springboot.controller;

import com.dmed.llm_powered_apps_with_springboot.service.ChatService;
import org.junit.jupiter.api.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(properties = {
        "spring.ai.openai.api-key=test",
        "logging.level.org.springframework.ai=DEBUG"
})
public class ChatServiceRelevancyTest {

    private final float minRelevancyScore = 0.7f;
    @Autowired
    ChatModel chatModel;
    @Autowired
    private ChatService chatService;
    private RelevancyEvaluator relevancyEvaluator;

    @BeforeEach
    void setUp() {
        ChatClient.Builder chatClientBuilder = ChatClient.builder(chatModel).defaultAdvisors(new SimpleLoggerAdvisor());
        chatClientBuilder.build();
        this.relevancyEvaluator = new RelevancyEvaluator(chatClientBuilder);
    }

    @Test
    @DisplayName("Relevancy Test general chat response")
    @Timeout(value = 30)
    public void testGeneralChatResponseRelevancy() {
        String prompt = "What is the capital of France?";
        String response = chatService.getGeneralChatResponse(prompt);
        EvaluationRequest evaluationRequest = new EvaluationRequest(prompt, response);
        EvaluationResponse evaluationResponse = relevancyEvaluator.evaluate(evaluationRequest);
        Assertions.assertAll(
                () -> assertThat(response).isNotBlank(),
                () -> assertThat(evaluationResponse.isPass())
                        .withFailMessage("""
                                ==================================================
                                The answer was not considered relevant to the question.
                                Prompt: %s
                                Response: %s
                                """, prompt, response)
                        .isTrue(),
                () -> assertThat(evaluationResponse.getScore())
                        .withFailMessage("""
                                ==================================================
                                The answer was relevant but the score was below the threshold.
                                Prompt: %s
                                Response: %s
                                Score: %.2f
                                Minimum Expected Score: %.2f
                                """, prompt, response, evaluationResponse.getScore(), minRelevancyScore)
                        .isGreaterThanOrEqualTo(minRelevancyScore));
    }
}


