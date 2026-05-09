package com.efeerturk.spring_openai.chat.service.impl;

import com.efeerturk.spring_openai.chat.service.RagIngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RagIngestionServiceImpl implements RagIngestionService {
    private final VectorStore vectorStore;
    @Override
    public String ingestCompanyData(String rawText){
        Document document=Document.builder()
                .text(rawText)
                .build();
        TokenTextSplitter splitter=TokenTextSplitter.builder()
                .withChunkSize(500)
                .build();
        List<Document>chunkedDocuments=splitter.apply(List.of(document));
        vectorStore.add(chunkedDocuments);
        return "Basarili : "+chunkedDocuments.size()+" adet veri blogu parcalandi ve vektor veritabanina kaydedildi";
    }

}
