package com.company.SafarSaathi.chat_service.controller;

import com.company.SafarSaathi.chat_service.dto.ChatMessageDto;
import com.company.SafarSaathi.chat_service.entity.ChatMessage;
import com.company.SafarSaathi.chat_service.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessageDto chatMessageDto) {
        ChatMessage message = ChatMessage.builder()
                .senderId(chatMessageDto.getSenderId())
                .receiverId(chatMessageDto.getReceiverId())
                .content(chatMessageDto.getContent())
                .timestamp(LocalDateTime.now())
                .seen(false)
                .build();

        chatMessageRepository.save(message);
        messagingTemplate.convertAndSend(
                "/queue/messages/" + chatMessageDto.getReceiverId(),
                chatMessageDto
        );
    }
}