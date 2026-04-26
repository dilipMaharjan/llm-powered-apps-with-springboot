package com.dmed.llm_powered_apps_with_springboot.controller;

import com.dmed.llm_powered_apps_with_springboot.service.ChatService;
import org.junit.jupiter.api.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(properties = {
        "spring.ai.openai.api-key=test",
        "logging.level.org.springframework.ai=DEBUG"
})
public class ChatServiceFactCheckTest {

    @Autowired
    ChatModel chatModel;
    ChatClient chatClient;
    @Autowired
    private ChatService chatService;
    private FactCheckingEvaluator factCheckingEvaluator;

    @BeforeEach
    void setup() {
        ChatClient.Builder chatClientBuilder =
                ChatClient.builder(chatModel).defaultAdvisors(new SimpleLoggerAdvisor());
        this.chatClient = chatClientBuilder.build();
        chatClientBuilder.build();
        this.factCheckingEvaluator = new FactCheckingEvaluator(chatClientBuilder);
    }

    @Test
    @DisplayName("Fact Test general chat response")
    @Timeout(value = 30)
    public void testGeneralChatResponseFact() {

        // Given
        String prompt = "Who discovered America?";
        String claim = "Christopher Columbus.";

        // When
        String response = chatService.getGeneralChatResponse(prompt);
        EvaluationRequest evaluationRequest = new EvaluationRequest(prompt, List.of(new Document(response)), claim);
        EvaluationResponse evaluationResponse = factCheckingEvaluator.evaluate(evaluationRequest);

        Assertions.assertAll(() -> assertThat(response).isNotBlank(),
                () -> assertThat(evaluationResponse.isPass())
                        .withFailMessage("""
                                ========================================
                                The answer was not considered factually correct.
                                Claim: "%s"
                                Response: "%s"
                                ========================================
                                """, claim, response)
                        .isTrue());

    }
}



