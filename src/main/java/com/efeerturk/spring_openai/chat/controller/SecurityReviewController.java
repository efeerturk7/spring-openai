package com.efeerturk.spring_openai.chat.controller;

import com.efeerturk.spring_openai.chat.service.SecurityReviewWorkflowAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/spring-ai/agent")
@RequiredArgsConstructor
public class SecurityReviewController {
    private final SecurityReviewWorkflowAgent workflowAgent;
    public record ReviewRequest(String url, String topic) {}

    @PostMapping("/security-review")
    public Mono<String> triggerSecurityReviewChain(@RequestBody ReviewRequest request) {

        return Mono.fromCallable(() ->
                workflowAgent.executeSecurityReviewChain(request.url(), request.topic())
        );
    }
}
