package com.efeerturk.spring_openai.chat.config;

import io.modelcontextprotocol.server.McpServer;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

public class VehiclePostureMcpServerConfig {
    @Bean
    @Description("Fetches the physical posture and inspection report of a vehicle by its chassis number.")
    public Function<String,String>vehiclePostureService(){
        return chassisNo->{
            return "Vehicle " + chassisNo + " has 2 physical dents on the left door. Engine health is 85%.";
        };
    }
    @Bean
    public McpServer vehiclePostureMcpServer(Function<String, String> vehiclePostureService) {

        ToolCallbackProvider tools = MethodToolCallbackProvider.builder()
                .toolObjects(vehiclePostureService)
                .build();

        // Server-Sent Events (SSE) or Streamable HTTP
        return McpServer.builder()
                .tools(tools)
                .transport(new SseServerTransport("/mcp/posture/sse", "/mcp/posture/messages"))
                .build();
    }
}
