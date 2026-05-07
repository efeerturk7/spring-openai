package com.efeerturk.spring_openai.chat;


import org.springframework.ai.chat.client.ChatClient;


import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
public class AIAssistantService {
    private final ChatClient chatClient;

    public AIAssistantService(ChatClient.Builder builder) {

        this.chatClient = builder.defaultAdvisors(new SafeGuardAdvisor()).build();
    }
    public Flux<String> generateCarAd(String brand, String model){
        return chatClient.prompt()
                .user(u ->u.text("Aşağıdaki araç bilgileri için SEO uyumlu, müşteriyi cezbedecek maksimum 2 cümlelik bir ilan başlığı oluştur.\\n\\nKurallar:\\n1. Çıktı KESİNLİKLE ve SADECE Türkçe olmalıdır.\\n2. Sadece başlığı yaz, ekstra açıklama veya 'Here is your headline' gibi giriş cümleleri kullanma.\\n\\nAraç: {brand} {model}")
                        .param("brand",brand)
                        .param("model",model))
                .options(OllamaChatOptions.builder().temperature(0.8))
                .stream()
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
    //basic chat method
    public String secureChat(String message,String chatId){
        ChatResponse chatResponse=chatClient.prompt().advisors(a-> a.param("chat_memory_conversation_id",chatId))
                .user(message)
                .call().chatResponse();
        System.out.printf("Maaliyet :"+chatResponse.getMetadata().getUsage().getTotalTokens()+" token");
        return chatResponse.getResult().getOutput().toString();
    }
}
