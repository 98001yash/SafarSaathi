package com.company.SafarSaathi.Bot_service.service;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BotService {

    private final RestTemplate restTemplate;

    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";

    public String getBotResponse(String userMessage) {
        // Prepare prompt
        String prompt = "You are a helpful travel assistant. Answer the user's query clearly and concisely:\n" + userMessage;

        // Create request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gemma");      // or mistral, llama3, etc.
        requestBody.put("prompt", prompt);
        requestBody.put("stream", false);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(OLLAMA_URL, requestEntity, Map.class);
            String result = (String) response.getBody().get("response");

            log.info("Ollama response: {}", result);
            return result;
        } catch (Exception ex) {
            log.error("Failed to get response from Ollama: {}", ex.getMessage());
            throw new RuntimeException("Bot service is currently unavailable.");
        }
    }
}