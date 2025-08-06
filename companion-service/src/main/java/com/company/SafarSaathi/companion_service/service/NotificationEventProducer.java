package com.company.SafarSaathi.companion_service.service;

import com.company.SafarSaathi.companion_service.dtos.NotificationEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendNotification(NotificationEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("notification-events", json);
            System.out.println("✅ Sent notification event to Kafka for userId: " + event.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}