package com.efeerturk.spring_openai.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.function.Function;

@Configuration
public class DiagramToolConfig {
    public record DiagramRequest(String mermaidCode, String fileName) {}
    public record DiagramResponse(String filePath, String status) {}

    @Bean
    @Description("Generates and saves a Mermaid diagram to the local file storage. Provide valid Mermaid JS syntax.")
    public Function<DiagramRequest, DiagramResponse> diagramGeneratorTool() {
        return request -> {
            try {
                Path directory = Paths.get("storage/diagrams");
                if (!Files.exists(directory)) {
                    Files.createDirectories(directory);
                }

                String safeFileName = (request.fileName() != null ? request.fileName() : UUID.randomUUID().toString()) + ".mmd";
                Path filePath = directory.resolve(safeFileName);

                Files.writeString(filePath, request.mermaidCode());

                return new DiagramResponse(filePath.toAbsolutePath().toString(), "SUCCESS");
            } catch (Exception e) {
                return new DiagramResponse("", "FAILED: " + e.getMessage());
            }
        };
    }
}
