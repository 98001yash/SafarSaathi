package com.company.SafarSaathi.Bot_service.service;

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
            Map<String, Object> body = new HashMap<>();
            body.put("model", "gemma");
            body.put("prompt", "You are a helpful travel assistant:\n" + userMessage);
            body.put("stream", false); // Disable streaming

            String jsonBody = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(body);

            URL url = new URL("http://localhost:11434/api/generate");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonBody.getBytes());
                os.flush();
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder fullResponse = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty() && line.startsWith("data:")) {
                        String json = line.substring(5).trim();
                        Map<String, Object> chunk = new com.fasterxml.jackson.databind.ObjectMapper().readValue(json, Map.class);
                        String token = (String) chunk.get("response");
                        if (token != null) {
                            fullResponse.append(token);
                        }
                    }
                }

                return fullResponse.toString();
            }

        } catch (Exception e) {
            log.error("Error while getting bot reply", e);
            return "Error: " + e.getMessage();
        }
    }

}
