package com.efeerturk.spring_openai.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/spring-ai")
public class RestAIAssistantController {
    private final AIAssistantService aiAssistantService;
    @GetMapping("/generate-ad")
    public String generateAd(@RequestParam String brand,@RequestParam String model){
        return aiAssistantService.generateCarAd(brand,model);
    }
    @PostMapping("/extract")
    public CarDto extract(@RequestBody String rawText){
        return aiAssistantService.carInfo(rawText);
    }
    @PostMapping("/classify")
    public String classify(@RequestBody String comment){
        return aiAssistantService.classifyCustomerComment(comment);
    }
}
