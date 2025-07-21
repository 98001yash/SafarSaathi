package com.company.SafarSaathi.companion_service.service;


import com.company.SafarSaathi.companion_service.auth.UserContextHolder;
import com.company.SafarSaathi.companion_service.dtos.CompanionDto;
import com.company.SafarSaathi.companion_service.entity.Companion;
import com.company.SafarSaathi.companion_service.exceptions.BadRequestException;
import com.company.SafarSaathi.companion_service.exceptions.ResourceNotFoundException;
import com.company.SafarSaathi.companion_service.repository.CompanionPreferenceRepository;
import com.company.SafarSaathi.companion_service.repository.CompanionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.nio.channels.AcceptPendingException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanionService {


    private final CompanionRepository companionRepository;
    private final CompanionPreferenceRepository companionPreferenceRepository;
    private final ModelMapper modelMapper;

    public CompanionDto  createCompanion(CompanionDto dto){
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Creating companion for userId: {}",userId);

        Companion companion = modelMapper.map(dto, Companion.class);
        companion.setUserId(userId);

        Companion saved = companionRepository.save(companion);
        log.info("Companion created with ID: {}",saved.getId());

        return modelMapper.map(saved, CompanionDto.class);
    }

    public CompanionDto updateCompanion(Long id, CompanionDto dto){
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Updating companion ID: {} by userId: {}",id, userId);


        Companion  existing  = companionRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("companion not found with id:"+id));

        if(!existing.getUserId().equals(userId)){
            throw new BadRequestException("You are not allowed to update this companion request");
        }

        modelMapper.map(dto, existing);
        Companion updated = companionRepository.save(existing);

        log.info("Companion ID: {} updated",updated.getId());
        return modelMapper.map(updated, CompanionDto.class);
    }

    public void deleteCompanion(Long id){
        Long userId = UserContextHolder.getCurrentUserId();

        log.info("Deleting companion ID: {} by userId: {}",id, userId);

        Companion companion = companionRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Companion not found with id: "+id));

        if(!companion.getUserId().equals(userId)){
            throw new BadRequestException("You are nnt allowed to delete this companion request.");
        }

        companionRepository.delete(companion);
        log.info("Companion ID: {} deleted", id);
    }
}
