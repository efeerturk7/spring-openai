package com.efeerturk.spring_openai.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

@Configuration
public class WebToolConfig {
    public record WebRequest(String url) {}
    public record WebResponse(String content,int statusCode) {}
    @Bean
    @Description("Fetches the raw text content from a given URL. Use this to analyze external security reports or policies.")
    public Function<WebRequest,WebResponse>webScraperTool(){
        return request ->{
            RestClient restClient=RestClient.create();
            try {
                String result=restClient.get()
                        .uri(request.url())
                        .retrieve()
                        .body(String.class);
                String truncated=result!=null&&result.length()>4000
                        ?result.substring(0,4000)+"..."
                        :result;
                return new WebResponse(truncated,200);

            }catch (Exception e){
                return new WebResponse(e.getMessage(),404);
            }
        };
    }
}
