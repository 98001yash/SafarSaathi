package com.company.SafarSaathi.notification_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEvent {

    private String userId;
    private String type;
    private String message;
    private String email;
    private String phoneNumber;
}
