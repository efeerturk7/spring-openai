package com.efeerturk.spring_openai.chat.service.impl;

import com.efeerturk.spring_openai.chat.service.AdvancedRagIngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdvancedRagIngestionServiceImpl implements AdvancedRagIngestionService {
    private final VectorStore vectorStore;
    private final ChatClient chatClient;
    @Override
    public void ingestWithMetadata(String text,String documentCategory){
        Document document=Document.builder()
                .text(text)
                .metadata(Map.of("category",documentCategory))
                .build();
        TokenTextSplitter splitter=TokenTextSplitter.builder()
                .withChunkSize(800)
                .withMinChunkSizeChars(100)
                .build();
        List<Document>chunks=splitter.apply(List.of(document));
        vectorStore.add(chunks);
    }
    @Override
    public String askWithAdvancedRag(String question,String categoryFilter){
        SearchRequest searchRequest = SearchRequest.builder()
                .topK(3)
                .filterExpression("category == '" + categoryFilter + "'")
                .build();
        List<Document>documents=vectorStore.similaritySearch(searchRequest);
        StringBuilder context=new StringBuilder();
        for (Document document : documents) {
            context.append(document.getFormattedContent()).append("\n\n");
        }
        return chatClient.prompt()
                .system("Sen şirketin resmi asistanısın. Lütfen sadece aşağıdaki şirket kurallarına göre cevap ver. Uydurma Yapma! ve SADECE TÜRKÇE CEVAP VER\n\nKURALLAR:\n"+context)
                .user(question)
                .call()
                .content();




    }
}
