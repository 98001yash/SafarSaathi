package com.company.SafarSaathi.notification_service.service;

import com.company.SafarSaathi.notification_service.dtos.NotificationEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationEventListener {

    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
    public void consumeNotification(NotificationEvent event) {
        log.info("📩 Received Notification Event: {}", event);
        log.info("✅ Notification processed for user {} - type: {}, msg: {}",
                event.getUserId(), event.getType(), event.getMessage());
    }
}

