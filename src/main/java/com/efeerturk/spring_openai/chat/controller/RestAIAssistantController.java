package com.efeerturk.spring_openai.chat.controller;

import com.efeerturk.spring_openai.chat.Dto.CarDto;
import com.efeerturk.spring_openai.chat.service.AIAssistantService;
import com.efeerturk.spring_openai.chat.service.AdvancedRagIngestionService;
import com.efeerturk.spring_openai.chat.service.RagIngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/spring-ai")
public class RestAIAssistantController {
    private final AIAssistantService aiAssistantService;
    private final RagIngestionService ragIngestionService;
    private final AdvancedRagIngestionService advancedRagIngestionService;
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
    @PostMapping(value = "/analyze-image",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String analyzeImage(@RequestParam("file") MultipartFile file,@RequestParam(defaultValue = "Bu fotoğraftaki arabanın markasını,rengini ve fiziksel durumunu (hasar,çizik vb.) detaylıca Türkçe olarak analiz et.")String message){
        try {
            return aiAssistantService.analyzeCarImage(file.getResource(),message);
        }catch (Exception e){
            throw new RuntimeException("Görsel işlenirken hata oluştu : "+e.getMessage());
        }
    }
    @PostMapping("/rag/ingest")
    public String ingestData(@RequestBody String companyPolicyText){
        return ragIngestionService.ingestCompanyData(companyPolicyText);
    }
    @GetMapping("/rag/ask")
    public String askQuestion(@RequestParam String question){
        return aiAssistantService.askToCompanyHandbook(question);
    }
    @PostMapping("/advanced-ingest")
    public void ingestWithMetadata(String text,String documentCategory){
         advancedRagIngestionService.ingestWithMetadata(text,documentCategory);
    }
    @GetMapping("/advanced-ask")
    public String askWithAdvancedRag(String question,String categoryFilter){
        return advancedRagIngestionService.askWithAdvancedRag(question,categoryFilter);
    }
}
