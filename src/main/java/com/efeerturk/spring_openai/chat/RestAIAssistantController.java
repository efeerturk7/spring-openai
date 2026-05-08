package com.efeerturk.spring_openai.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/spring-ai")
public class RestAIAssistantController {
    private final AIAssistantService aiAssistantService;
    @GetMapping("/generate-ad")
    public Flux<String> generateCarAd(@RequestParam String brand, @RequestParam String model){
        return aiAssistantService.generateCarAd(brand,model);
    }
    @PostMapping("/extract")
    public CarDto extract(@RequestBody String rawText){
        return aiAssistantService.carInfo(rawText);
    }
    @PostMapping("/classify")
    public String classifySecure(@RequestBody String comment){
        return aiAssistantService.classifyCommentSecurely(comment);
    }
    @GetMapping("/secure-chat")
    public String secureChat(@RequestParam String message,@RequestParam String chatId) {
        return aiAssistantService.secureChat(message,chatId);
    }
    @GetMapping("/embed")
    public float[]embedText(@RequestParam String text){
        return aiAssistantService.generateEmbedding(text);
    }
}
