package com.efeerturk.spring_openai.chat.service;

public interface AdvancedRagIngestionService {
    void ingestWithMetadata(String text,String documentCategory);
     String askWithAdvancedRag(String question,String categoryFilter);
}
