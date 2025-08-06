package com.company.SafarSaathi.notification_service.service;

import com.company.SafarSaathi.notification_service.dtos.NotificationEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationEventListener {

    @KafkaListener(topics = "notification-events", groupId = "notification-service-group")
    public void consumeNotificationEvent(String eventJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            NotificationEvent event = objectMapper.readValue(eventJson, NotificationEvent.class);

            // Handle the event here (send email, WhatsApp etc.)
            System.out.println("📩 Received notification for userId: " + event.getUserId());
            System.out.println("📨 Message: " + event.getMessage());
            System.out.println("📧 Email: " + event.getEmail());
            System.out.println("📱 Phone: " + event.getPhoneNumber());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
