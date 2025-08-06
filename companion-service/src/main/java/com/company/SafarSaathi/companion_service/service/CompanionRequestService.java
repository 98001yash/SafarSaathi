package com.company.SafarSaathi.companion_service.service;

import com.company.SafarSaathi.companion_service.auth.UserContextHolder;
import com.company.SafarSaathi.companion_service.dtos.CompanionRequestDto;
import com.company.SafarSaathi.companion_service.dtos.CompanionRequestResponseDto;
import com.company.SafarSaathi.companion_service.dtos.NotificationEvent;
import com.company.SafarSaathi.companion_service.entity.CompanionRequest;
import com.company.SafarSaathi.companion_service.enums.RequestStatus;
import com.company.SafarSaathi.companion_service.exceptions.BadRequestException;
import com.company.SafarSaathi.companion_service.exceptions.ResourceNotFoundException;
import com.company.SafarSaathi.companion_service.repository.CompanionRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanionRequestService {

    private final CompanionRequestRepository companionRequestRepository;
    private final ModelMapper modelMapper;
    private final NotificationEventProducer notificationEventProducer;

    public CompanionRequestResponseDto sendRequest(CompanionRequestDto dto) {
        Long senderId = UserContextHolder.getCurrentUserId();

        if (senderId.equals(dto.getReceiverId())) {
            throw new IllegalArgumentException("You cannot send a request to yourself.");
        }

        companionRequestRepository.findBySenderIdAndReceiverIdAndTripId(senderId, dto.getReceiverId(), dto.getTripId())
                .ifPresent(existing -> {
                    throw new IllegalStateException("Request already exists for this user and trip.");
                });

        CompanionRequest request = new CompanionRequest();
        request.setSenderId(senderId);
        request.setReceiverId(dto.getReceiverId());
        request.setTripId(dto.getTripId());
        request.setStatus(RequestStatus.PENDING);
        request.setTimeStamp(LocalDateTime.now());

        CompanionRequest saved = companionRequestRepository.save(request);

        log.info("✅ Companion request sent from {} to {} for trip {}", senderId, dto.getReceiverId(), dto.getTripId());

        // 📨 TEMP: Hardcoded receiver email (replace with user-service call later)
        String receiverEmail = "harshgarg5907@gmail.com";
        // 🔔 Send Kafka Notification to receiver
        NotificationEvent event = NotificationEvent.builder()
                .userId(dto.getReceiverId().toString())
                .type("REQUEST_RECEIVED")
                .message("You have a new companion request from user " + senderId + " for trip " + dto.getTripId())
                .email(receiverEmail)
                .build();

        notificationEventProducer.sendNotification(event);

        return modelMapper.map(saved, CompanionRequestResponseDto.class);
    }

    public CompanionRequestResponseDto acceptRequest(Long requestId) {
        Long currentUserId = UserContextHolder.getCurrentUserId();
        log.info("Attempting to accept request with ID: {}", requestId);
        log.info("Current user ID from context: {}", currentUserId);

        CompanionRequest request = companionRequestRepository.findById(requestId)
                .orElseThrow(() -> {
                    log.error("Request ID {} not found for ACCEPT", requestId);
                    return new ResourceNotFoundException("Request not found");
                });

        log.info("Fetched request details -> Sender: {}, Receiver: {}, Trip: {}, Status: {}",
                request.getSenderId(), request.getReceiverId(), request.getTripId(), request.getStatus());

        if (!request.getReceiverId().equals(currentUserId)) {
            log.warn("Unauthorized accept attempt. Receiver ID: {}, Current User ID: {}",
                    request.getReceiverId(), currentUserId);
            throw new BadRequestException("You are not authorized to accept this request.");
        }

        request.setStatus(RequestStatus.ACCEPTED);
        companionRequestRepository.save(request);

        log.info("User {} accepted companion request ID {} from sender {}",
                currentUserId, requestId, request.getSenderId());

        // 🔔 Send Kafka Notification to sender
        notificationEventProducer.sendNotification(
                new NotificationEvent(
                        request.getSenderId().toString(),
                        "REQUEST_ACCEPTED",
                        "Your companion request has been accepted by user " + currentUserId + " for trip " + request.getTripId(),
                        null,
                        null
                )
        );

        return modelMapper.map(request, CompanionRequestResponseDto.class);
    }

    public CompanionRequestResponseDto rejectRequest(Long requestId) {
        Long currentUserId = UserContextHolder.getCurrentUserId();

        CompanionRequest request = companionRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        if (!request.getReceiverId().equals(currentUserId)) {
            throw new BadRequestException("You are not authorized to reject this request.");
        }

        request.setStatus(RequestStatus.REJECTED);
        companionRequestRepository.save(request);

        log.info("User {} rejected companion request ID {} from sender {}",
                currentUserId, requestId, request.getSenderId());

        // 🔔 Send Kafka Notification to sender
        notificationEventProducer.sendNotification(
                new NotificationEvent(
                        request.getSenderId().toString(),
                        "REQUEST_REJECTED",
                        "Your companion request has been rejected by user " + currentUserId + " for trip " + request.getTripId(),
                        null,
                        null
                )
        );

        return modelMapper.map(request, CompanionRequestResponseDto.class);
    }


    public List<CompanionRequestResponseDto> getRequestsForUser() {
        Long userId = UserContextHolder.getCurrentUserId();
        List<CompanionRequest> requests = companionRequestRepository.findByReceiverId(userId);

        log.info("Retrieved {} requests for user: {}", requests.size(), userId);

        return requests.stream()
                .map(req -> modelMapper.map(req, CompanionRequestResponseDto.class))
                .collect(Collectors.toList());
    }

    public List<CompanionRequestResponseDto> getSentRequests() {
        Long userId = UserContextHolder.getCurrentUserId();
        List<CompanionRequest> requests = companionRequestRepository.findBySenderId(userId);

        log.info("Retrieved {} sent requests by user: {}", requests.size(), userId);

        return requests.stream()
                .map(req -> modelMapper.map(req, CompanionRequestResponseDto.class))
                .collect(Collectors.toList());
    }
}
