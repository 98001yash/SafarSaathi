package com.company.SafarSaathi.companion_service.service;


import com.company.SafarSaathi.companion_service.auth.UserContextHolder;
import com.company.SafarSaathi.companion_service.dtos.*;
import com.company.SafarSaathi.companion_service.entity.Companion;
import com.company.SafarSaathi.companion_service.exceptions.BadRequestException;
import com.company.SafarSaathi.companion_service.exceptions.ResourceNotFoundException;
import com.company.SafarSaathi.companion_service.repository.CompanionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanionService {


    private final CompanionRepository companionRepository;
    private final ModelMapper modelMapper;


    public CompanionDto createCompanion(CreateCompanionRequest dto) {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Creating companion for userId: {}", userId);

        Companion companion = new Companion();
        companion.setUserId(userId);
        companion.setTripId(dto.getTripId());
        companion.setStatus(dto.getStatus());
        companion.setMessage(dto.getMessage());

        Companion saved = companionRepository.save(companion);
        log.info("Companion created with ID: {}", saved.getId());

        return modelMapper.map(saved, CompanionDto.class);
    }


    public CompanionDto updateCompanion(Long id, UpdateCompanionRequest dto) {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Updating companion ID: {} by userId: {}", id, userId);

        Companion existing = companionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Companion not found with id: " +userId));

        if (!existing.getUserId().equals(userId)) {
            throw new BadRequestException("You are not allowed to update this companion request");
        }

        if (dto.getTripId() != null) existing.setTripId(dto.getTripId());
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
        if (dto.getMessage() != null) existing.setMessage(dto.getMessage());

        Companion updated = companionRepository.save(existing);
        log.info("Companion ID: {} updated", updated.getId());

        CompanionDto companionDto = CompanionDto.builder()
                .id(updated.getId())
                .tripId(updated.getTripId())
                .userId(updated.getUserId())
                .status(updated.getStatus())
                .message(updated.getMessage())
                .matchedUserIds(new HashSet<>(updated.getMatchedUserIds()))
                .build();

        return companionDto;
    }




    public void deleteCompanion(Long id){
        Long userId = UserContextHolder.getCurrentUserId();

        log.info("Deleting companion ID: {} by userId: {}",id, userId);

        Companion companion = companionRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Companion not found with id: "+id+ userId));

        if(!companion.getUserId().equals(userId)){
            throw new BadRequestException("You are nnt allowed to delete this companion request.");
        }

        companionRepository.delete(companion);
        log.info("Companion with ID: {} deleted", id);
    }

    public List<CompanionDto> getAllCompanions(){
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Fetching all companions except for userid: {}",userId);

        List<Companion> companions = companionRepository.findByUserId(userId);

        return companions.stream()
                .map(companion->modelMapper.map(companion,CompanionDto.class))
                .collect(Collectors.toList());
    }
}
