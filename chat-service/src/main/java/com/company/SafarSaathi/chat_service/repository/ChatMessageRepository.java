package com.company.SafarSaathi.chat_service.repository;

import com.company.SafarSaathi.chat_service.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {


    List<ChatMessage> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
            Long senderId, Long receiverId, Long receiverId2, Long senderId2);
}
