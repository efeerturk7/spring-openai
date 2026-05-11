package com.efeerturk.spring_openai.chat.config;

import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class RagToolConfig {
    public record RagRequest(String query,String category){}
    public record RagResponse(String context){}
    @Bean
    @Description("Searches internal corporate documents and security policies using vector database. Provide a search query and optional category.")
    public Function<RagRequest,RagResponse>ragSearchTool(VectorStore vectorStore){
        return request ->{
            var searchRequest= SearchRequest.builder()
                    .query(request.query())
                    .topK(3)
                    .build();
            String context=vectorStore.similaritySearch(searchRequest)
                    .stream()
                    .map(doc -> doc.getFormattedContent())
                    .collect(Collectors.joining("\n---\n"));
            return new RagResponse(context.isEmpty()?"No internal documents found." : context);
        };
    }

}
