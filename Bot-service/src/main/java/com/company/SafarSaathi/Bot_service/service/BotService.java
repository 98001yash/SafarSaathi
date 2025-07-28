package com.company.SafarSaathi.Bot_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class BotService {

    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SseEmitter streamWithSse(String userMessage) {
        SseEmitter emitter = new SseEmitter(0L); // no timeout

        new Thread(() -> {
            try {
                String prompt = "You are a helpful travel assistant. Answer clearly:\n" + userMessage;

                // Setup HTTP POST request
                URL url = new URL(OLLAMA_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Send JSON payload
                String requestBody = String.format("""
                        {
                          "model": "gemma",
                          "prompt": "%s",
                          "stream": true
                        }
                        """, prompt.replace("\"", "\\\""));

                try (OutputStream os = connection.getOutputStream()) {
                    os.write(requestBody.getBytes(StandardCharsets.UTF_8));
                }

                // Read response stream line by line
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;

                    while ((line = reader.readLine()) != null) {
                        if (line.trim().isEmpty()) continue;

                        JsonNode json = objectMapper.readTree(line);
                        String chunk = json.get("response").asText();

                        emitter.send(chunk);
                    }
                }

                emitter.complete();
            } catch (Exception e) {
                try {
                    emitter.send("Error: " + e.getMessage());
                } catch (Exception ignored) {}
                emitter.completeWithError(e);
            }
        }).start();

        return emitter;
    }
}
