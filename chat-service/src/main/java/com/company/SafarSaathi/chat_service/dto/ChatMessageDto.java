package com.company.SafarSaathi.chat_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageDto {

    private Long senderId;
    private Long receiverId;
    private String content;
}
