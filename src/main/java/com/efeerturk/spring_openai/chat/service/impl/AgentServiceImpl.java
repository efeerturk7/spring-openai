package com.efeerturk.spring_openai.chat.service.impl;


import com.efeerturk.spring_openai.chat.service.AgentService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;


@Service
public class AgentServiceImpl implements AgentService {
    private final ChatClient chatClient;
    public AgentServiceImpl(ChatClient.Builder chatClient) {
        this.chatClient= chatClient.build();
    }
    @Override
    public String askAgent(String question){
        return chatClient.prompt()
                .system("Sen Gallerist A.Ş.'nin zeki satış danışmanısın. Müşterilere stok bilgisi verirken SADECE sistemden gelen güncel verileri kullan. Fiyat uydurma!")
                .user(question)
                .tools("carStockCheckTool")
                .call()
                .content();

    }
}
