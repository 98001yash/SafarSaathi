package com.company.SafarSaathi.companion_service.service;


import com.company.SafarSaathi.companion_service.dtos.CompanionRequestDto;
import com.company.SafarSaathi.companion_service.dtos.CompanionRequestResponseDto;
import com.company.SafarSaathi.companion_service.entity.CompanionRequest;
import com.company.SafarSaathi.companion_service.enums.RequestStatus;
import com.company.SafarSaathi.companion_service.exceptions.ResourceNotFoundException;
import com.company.SafarSaathi.companion_service.repository.CompanionRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanionRequestService {

    private final CompanionRequestRepository companionRequestRepository;
    private final ModelMapper modelMapper;

    public CompanionRequestResponseDto sendRequest(CompanionRequestDto dto){
        CompanionRequest request = new CompanionRequest();

        request.setSenderId(dto.getSenderId());
        request.setReceiverId(dto.getReceiverId());
        request.setTripId(dto.getTripId());
        request.setStatus(RequestStatus.PENDING);
        request.setTimeStamp(LocalDateTime.now());

        CompanionRequest saved = companionRequestRepository.save(request);
        log.info("New companion Request sent from {} to {}",dto.getSenderId(), dto.getReceiverId());

        return modelMapper.map(saved, CompanionRequestResponseDto.class);
    }


    public CompanionRequestResponseDto acceptRequest(Long requestId){
        CompanionRequest request = companionRequestRepository.findById(requestId)
                .orElseThrow(()->{
                    log.error("Request ID {} not found with ACCEPT",requestId);
                    return new ResourceNotFoundException("Request not found");
                });

        request.setStatus(RequestStatus.ACCEPTED);
        companionRequestRepository.save(request);
        log.info("Request ID {} accepted",requestId);

        return modelMapper.map(request, CompanionRequestResponseDto.class);
    }


    public CompanionRequestResponseDto rejectRequest(Long requestId){
        CompanionRequest request = companionRequestRepository.findById(requestId)
                .orElseThrow(()->{
                    log.error("Request ID {} not found for REJECT",requestId);
                    return new ResourceNotFoundException("Request not found");
                });

        request.setStatus(RequestStatus.REJECTED);
        companionRequestRepository.save(request);
        log.info("Request ID {} rejected",requestId);

        return modelMapper.map(request, CompanionRequestResponseDto.class);
    }
}
