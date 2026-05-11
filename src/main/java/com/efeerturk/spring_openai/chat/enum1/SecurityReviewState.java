package com.efeerturk.spring_openai.chat.enum1;

import jakarta.persistence.Table;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Table(name = "security_review_state")
public record SecurityReviewState(
        @Id
        Long id,
        String targetUrl,
        String reviewTopic,
        String aiGeneratedPlan, // AI'ın onayımıza sunduğu plan
        String serializedContext, // O ana kadarki sohbet geçmişi (JSON)
        ReviewStatus status,
        Instant createdAt,
        Instant updatedAt
) {}
