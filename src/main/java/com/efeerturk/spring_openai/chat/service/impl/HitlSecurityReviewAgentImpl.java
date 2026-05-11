package com.efeerturk.spring_openai.chat.service.impl;

import com.efeerturk.spring_openai.chat.enum1.ReviewStatus;
import com.efeerturk.spring_openai.chat.enum1.SecurityReviewState;
import com.efeerturk.spring_openai.chat.service.HitlSecurityReviewAgent;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import repository.ReviewStateRepository;

import java.time.Instant;

@Service
public class HitlSecurityReviewAgentImpl implements HitlSecurityReviewAgent {
    private final ChatClient chatClient;
    private final ReviewStateRepository stateRepository;

    public HitlSecurityReviewAgentImpl(ChatClient.Builder builder, ReviewStateRepository repo) {
        this.chatClient = builder.build();
        this.stateRepository = repo;
    }

    @Override
    public Mono<SecurityReviewState> initiateReviewAndPause(String url, String topic) {

        String prompt = "Create a step-by-step security review plan for %s at %s. Return only the plan.".formatted(topic, url);

        return Mono.fromCallable(() -> chatClient.prompt().user(prompt).call().content())
                .flatMap(plan -> {

                    SecurityReviewState newState = new SecurityReviewState(
                            null, url, topic, plan,
                            "{}",
                            ReviewStatus.WAITING_HUMAN_APPROVAL,
                            Instant.now(), Instant.now()
                    );
                    return stateRepository.save(newState);
                });
    }

    @Override
    public Mono<String> resumeAndExecute(Long stateId) {
        return stateRepository.findById(stateId)
                .flatMap(state -> {
                    if (state.status() != ReviewStatus.APPROVED) {
                        return Mono.error(new IllegalStateException("Cannot execute: State is not APPROVED"));
                    }

                    String executionPrompt = """
                            Here is the approved plan: %s
                            Please execute this plan using your tools (ragSearchTool, webScraperTool) 
                            and generate the final report.
                            """.formatted(state.aiGeneratedPlan());

                    return Mono.fromCallable(() ->
                            chatClient.prompt().user(executionPrompt).call().content()
                    ).flatMap(finalReport -> {
                        SecurityReviewState completedState = new SecurityReviewState(
                                state.id(), state.targetUrl(), state.reviewTopic(),
                                state.aiGeneratedPlan(), state.serializedContext(),
                                ReviewStatus.COMPLETED, state.createdAt(), Instant.now()
                        );
                        return stateRepository.save(completedState).thenReturn(finalReport);
                    });
                });
    }
}
