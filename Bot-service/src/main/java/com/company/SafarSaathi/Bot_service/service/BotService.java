package com.company.SafarSaathi.Bot_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class BotService {

    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";

    public String getBotReply(String userMessage) {
        try {
            // Prepare request body
            Map<String, Object> body = new HashMap<>();
            body.put("model", "gemma");
            body.put("prompt", "You are a helpful travel assistant:\n" + userMessage);
            body.put("stream", false); // disable streaming for full response

            // Convert to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(body);

            // Setup HTTP connection
            URL url = new URL(OLLAMA_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            // Send request
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonBody.getBytes());
                os.flush();
            }

            // Read full response
            StringBuilder responseBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
            }

            // Parse JSON and extract "response"
            Map<String, Object> responseMap = objectMapper.readValue(responseBuilder.toString(), Map.class);
            return (String) responseMap.get("response");

        } catch (Exception e) {
            log.error("Error while getting bot reply", e);
            return "Error: " + e.getMessage();
        }
    }


}
