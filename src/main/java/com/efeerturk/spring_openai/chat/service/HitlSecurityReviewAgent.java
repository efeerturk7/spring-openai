package com.efeerturk.spring_openai.chat.service;

import com.efeerturk.spring_openai.chat.enum1.SecurityReviewState;
import reactor.core.publisher.Mono;

public interface HitlSecurityReviewAgent {
     Mono<SecurityReviewState> initiateReviewAndPause(String url, String topic);
     Mono<String> resumeAndExecute(Long stateId);
}
