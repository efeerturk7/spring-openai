package com.efeerturk.spring_openai.chat.service;

import com.efeerturk.spring_openai.chat.Dto.CarDto;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Flux;

public interface AIAssistantService {
     String analyzeCarImage(Resource imageResource, String prompt);
     float[] generateEmbedding(String text);
     Flux<String> generateCarAd(String brand, String model);
     CarDto carInfo(String rawUserInput);
     String classifyCommentSecurely(String comment);
    String secureChat(String message,String chatId);
     String askToCompanyHandbook(String question);
}
