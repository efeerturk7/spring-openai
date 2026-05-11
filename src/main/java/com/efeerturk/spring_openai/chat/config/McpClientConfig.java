package com.efeerturk.spring_openai.chat.config;

import io.modelcontextprotocol.client.McpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpClientConfig {
    @Bean
    public McpClient vehiclePostureMcpClient() {

        var transport = new ServerSentEventsTransport("http://localhost:8082/mcp/posture/sse");


        McpClient mcpClient = McpClient.sync(transport).build();

        mcpClient.toString();

        return mcpClient;
    }
}
