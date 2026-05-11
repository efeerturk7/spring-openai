package com.efeerturk.spring_openai.chat.service;

import io.modelcontextprotocol.client.McpClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class McpAgentImpl {
    private final ChatClient chatClient;


    public McpAgentImpl(ChatClient.Builder builder, McpClient postureMcpClient) {

        this.chatClient = builder
                .defaultSystem("You are an advanced Vehicle Inspector AI. " +
                        "Use the provided tools to fetch real-time data.")
                //tools was enjected
                .defaultTools(postureMcpClient.listTools())
                .build();
    }

    public Mono<String> analyzeVehicle(String chassisNo) {
        String prompt = "Please check the inspection posture of vehicle with chassis " + chassisNo +
                " and write a summary for the customer.";

        return Mono.fromCallable(() ->
                chatClient.prompt().user(prompt).call().content()
        );
    }
}
