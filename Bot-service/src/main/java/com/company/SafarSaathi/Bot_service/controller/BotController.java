package com.company.SafarSaathi.Bot_service.controller;

import com.company.SafarSaathi.Bot_service.service.BotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
public class BotController {

    private final BotService botService;

    @PostMapping(value = "/ask", produces = "application/json")
    public ResponseEntity<String> askBot(@RequestBody Map<String, String> payload) {
        String message = payload.get("message");

        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }

        log.info("Responding with full message for: {}", message);
        String response = botService.getBotReply(message);

        return ResponseEntity.ok(response);
    }
}