package com.efeerturk.spring_openai.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;



@Service
public class AIAssistantService {
    private final ChatClient chatClient;
    public AIAssistantService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }
    public String generateCarAd(String brand,String model){
        return chatClient.prompt()
                .user(u ->u.text("Bana {brand} {model} aracı için SEO uyumlu, 2 cümlelik havalı bir ilan başlığı yaz.")
                        .param("brand",brand)
                        .param("model",model))
                .call()
                .content();

    }
    public CarDto carInfo(String rawUserInput){
        return chatClient.prompt()
                .user(rawUserInput)
                .call()
                .entity(CarDto.class);
    }
    public String classifyCustomerComment(String comment){
        return chatClient.prompt()
                .system("Sen bir otomobil galerisi moderatörüsün. Sana gelen müşteri yorumunu okuyup sadece 3 kelimeden biriyle cevap vermelisin: OLUMLU,OLUMSUZ,SPAM. Asla başka bir şey yazma")
                .user(comment)
                .call()
                .content();
    }
}
