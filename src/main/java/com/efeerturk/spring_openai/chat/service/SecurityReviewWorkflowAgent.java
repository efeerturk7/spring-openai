package com.efeerturk.spring_openai.chat.service;

public interface SecurityReviewWorkflowAgent {
    public String executeSecurityReviewChain(String targetUrl, String reviewTopic);
}
