package com.company.SafarSaathi.companion_service.service;


import com.company.SafarSaathi.companion_service.neo4j_entities.CompanionRequestRelation;
import com.company.SafarSaathi.companion_service.neo4j_entities.UserNode;
import com.company.SafarSaathi.companion_service.repository.UserNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestGraphService {

    private final UserNodeRepository userNodeRepository;

    public void saveRequestToGraph(Long senderId, Long receiverId, Long tripId, String status) {
        UserNode sender = userNodeRepository.findById(senderId)
                .orElse(UserNode.builder()
                        .userId(senderId)
                        .sentRequests(new ArrayList<>())
                        .build());

        UserNode receiver = userNodeRepository.findById(receiverId)
                .orElse(UserNode.builder()
                        .userId(receiverId)
                        .build());

        CompanionRequestRelation relation = CompanionRequestRelation.builder()
                .receiver(receiver)
                .tripId(tripId)
                .timeStamp(LocalDateTime.now())
                .status(status)
                .build();

        sender.getSentRequests().add(relation);
        userNodeRepository.save(sender);
    }
}
