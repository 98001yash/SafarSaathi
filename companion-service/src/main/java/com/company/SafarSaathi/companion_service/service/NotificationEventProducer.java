package com.company.SafarSaathi.companion_service.service;

import com.company.SafarSaathi.companion_service.dtos.NotificationEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendNotification(NotificationEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("notification-events", json);
            log.info("✅ Sent notification to Kafka for userId: {}", event.getUserId());
        } catch (Exception e) {
            log.error("❌ Failed to send notification event", e);
        }
    }
}