package com.efeerturk.spring_openai.chat;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import reactor.core.publisher.Flux;

public class SafeGuardAdvisor implements CallAdvisor, StreamAdvisor {
    //for normal request
    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        checkSecurity(chatClientRequest);
        return callAdvisorChain.nextCall(chatClientRequest);
    }
    //for stream request
    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest, StreamAdvisorChain streamAdvisorChain) {
        checkSecurity(chatClientRequest);//security layer
        return streamAdvisorChain.nextStream(chatClientRequest);

    }

    @Override
    public String getName() {
        return "SafeGuardAdvisor";
    }

    @Override
    public int getOrder() {
        return 0;
    }
    private void checkSecurity(ChatClientRequest chatClientRequest) {
        String promptText=chatClientRequest.prompt().toString().toLowerCase();
        if (promptText.contains("password")||promptText.contains("şifre")||promptText.contains("sifre")||promptText.contains("unut")||promptText.contains("veritabanı")||promptText.contains("database")){
            System.err.println("istek reddedildi");
            throw new SecurityException();
        }
    }
}
