package com.company.SafarSaathi.notification_service.service;

import com.company.SafarSaathi.notification_service.dtos.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationEventListener {

    private final EmailService emailService;

    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
    public void consumeNotification(NotificationEvent event) {
        log.info("📩 Received Notification Event: {}", event);
        log.info("✅ Notification processed for user {} - type: {}, msg: {}",
                event.getUserId(), event.getType(), event.getMessage());

        if (event.getEmail() != null) {
            String subject = "🚨 New Notification from Safar Saathi";
            emailService.sendSimpleEmail(event.getEmail(), subject, event.getMessage());
        }
    }
}

