package com.company.SafarSaathi.Bot_service.controller;

import com.company.SafarSaathi.Bot_service.service.BotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
public class BotController {

    private final BotService botService;

    @PostMapping("/ask")
    public ResponseEntity<String> askBot(@RequestBody Map<String, String> payload) {
        String userMessage = payload.get("message");

        if (userMessage == null || userMessage.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Message cannot be empty.");
        }

        log.info("Received message from user: {}", userMessage);
        String botReply = botService.getBotResponse(userMessage);

        return ResponseEntity.ok(botReply);
    }
}
