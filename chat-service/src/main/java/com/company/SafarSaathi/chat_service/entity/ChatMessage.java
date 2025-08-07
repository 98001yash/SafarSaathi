package com.company.SafarSaathi.chat_service.entity;


import com.company.SafarSaathi.chat_service.enums.MessageStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long senderId;
    private Long receiverId;

    private String content;
    private LocalDateTime timestamp;


    @Enumerated(EnumType.STRING)
    private MessageStatus status;
}
