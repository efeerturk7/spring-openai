package com.efeerturk.spring_openai.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;


import java.util.function.Function;

@Configuration
public class AgentToolsConfig {
    public record StockRequest(String carModel){
    }
    public record StockResponse(boolean isAvailable, int stockCount, double currentPrice){}
    @Bean
    @Description("Müşterinin sorduğu araba modelinin stokta olup olmadığını, stok sayısını ve güncel fiyatını veritabanından sorgular.")
    public Function<StockRequest,StockResponse>carStockCheckTool(){
        return request->{
            System.out.println("[AJAN DEVREDE] Veritabanında stok aranıyor: " + request.carModel());
            String requestedCar=request.carModel.toLowerCase();
            if (requestedCar.contains("passat")){
                return new StockResponse(true,3,1850000.0);
            }else if (requestedCar.contains("civic")){
                return new StockResponse(true,5,1450000.0);
            }else {
                return new StockResponse(false,0,0);
            }
        };

    }
}
