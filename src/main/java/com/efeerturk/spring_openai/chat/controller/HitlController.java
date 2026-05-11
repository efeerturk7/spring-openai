package com.efeerturk.spring_openai.chat.controller;

import com.efeerturk.spring_openai.chat.enum1.ReviewStatus;
import com.efeerturk.spring_openai.chat.enum1.SecurityReviewState;
import com.efeerturk.spring_openai.chat.service.HitlSecurityReviewAgent;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import repository.ReviewStateRepository;

import java.time.Instant;

@RestController
@RequestMapping("/api/spring-ai/hitl")
@RequiredArgsConstructor
public class HitlController {
    private final HitlSecurityReviewAgent agent;
    private ReviewStateRepository repository;
    public record StartRequest(String url, String topic) {}


    @PostMapping("/start")
    public Mono<Long> startReview(@RequestBody StartRequest request) {
        return agent.initiateReviewAndPause(request.url(), request.topic())
                .map(state -> state.id());
    }


    @PostMapping("/{id}/approve")
    public Mono<String> approveReview(@PathVariable Long id) {
        return repository.findById(id)
                .flatMap(state -> {

                    var approvedState = new SecurityReviewState(
                            state.id(), state.targetUrl(), state.reviewTopic(),
                            state.aiGeneratedPlan(), state.serializedContext(),
                            ReviewStatus.APPROVED, state.createdAt(), Instant.now()
                    );
                    return repository.save(approvedState);
                })
                .flatMap(savedState -> agent.resumeAndExecute(savedState.id()));// Ajanı tetikle
    }

    // 3. İnsan Reddi (Reject)
    @PostMapping("/{id}/reject")
    public Mono<String> rejectReview(@PathVariable Long id) {
        return repository.findById(id)
                .flatMap(state -> {
                    var rejectedState = new SecurityReviewState(
                            state.id(), state.targetUrl(), state.reviewTopic(),
                            state.aiGeneratedPlan(), state.serializedContext(),
                            ReviewStatus.REJECTED, state.createdAt(), Instant.now()
                    );
                    return repository.save(rejectedState)
                            .thenReturn("Review has been securely aborted.");
                });
    }
}
