package com.efeerturk.spring_openai.chat.service.impl;

import com.efeerturk.spring_openai.chat.service.SecurityReviewWorkflowAgent;
import org.springframework.ai.chat.client.ChatClient;

public class SecurityReviewWorkflowAgentImpl implements SecurityReviewWorkflowAgent {
    private final ChatClient chatClient;

    public SecurityReviewWorkflowAgentImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultSystem("You are an elite Cloud Security Architect and Posture Reviewer. " +
                        "Your job is to analyze architectures, find vulnerabilities, and document them.")
                .defaultToolNames("webScraperTool","ragSearchTool","diagramGeneratorTool" )
                .build();
    }
    @Override
    public String executeSecurityReviewChain(String targetUrl, String reviewTopic) {


        String step1Prompt = """
                Analyze the security posture for the following topic: %s.
                First, use the webScraperTool to fetch external context from: %s
                Second, use the ragSearchTool to find our internal company policies on this topic.
                Summarize your findings.
                """.formatted(reviewTopic, targetUrl);

        String contextSummary = chatClient.prompt()
                .user(step1Prompt)
                .call()
                .content();


        String step2Prompt = """
                Based on the following security context:
                %s
                
                1. Identify the top 3 vulnerabilities.
                2. Use the diagramGeneratorTool to create a Mermaid.js diagram illustrating the attack vector.
                Return only the final executive summary.
                """.formatted(contextSummary);

        String finalReport = chatClient.prompt()
                .user(step2Prompt)
                .call()
                .content();

        return finalReport;
    }
}
