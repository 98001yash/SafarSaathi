package com.company.SafarSaathi.companion_service.service;


import com.company.SafarSaathi.companion_service.auth.UserContextHolder;
import com.company.SafarSaathi.companion_service.dtos.CompanionDto;
import com.company.SafarSaathi.companion_service.dtos.CompanionPreferenceDto;
import com.company.SafarSaathi.companion_service.entity.Companion;
import com.company.SafarSaathi.companion_service.entity.CompanionPreference;
import com.company.SafarSaathi.companion_service.exceptions.BadRequestException;
import com.company.SafarSaathi.companion_service.exceptions.ResourceNotFoundException;
import com.company.SafarSaathi.companion_service.repository.CompanionPreferenceRepository;
import com.company.SafarSaathi.companion_service.repository.CompanionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(()->new ResourceNotFoundException("companion not found with id:"+id, userId));

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
                .orElseThrow(()->new ResourceNotFoundException("Companion not found with id: "+id, userId));

        if(!companion.getUserId().equals(userId)){
            throw new BadRequestException("You are nnt allowed to delete this companion request.");
        }

        companionRepository.delete(companion);
        log.info("Companion ID: {} deleted", id);
    }

    public List<CompanionDto> getAllCompanions(){
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Fetching all companions except for userid: {}",userId);

        List<Companion> companions = companionRepository.findByUserId(userId);

        return companions.stream()
                .map(companion->modelMapper.map(companion,CompanionDto.class))
                .collect(Collectors.toList());
    }


    public CompanionPreferenceDto createOrUpdatePreference(CompanionPreferenceDto dto){
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Setting preference for userId: {}",userId);

        CompanionPreference preference = companionPreferenceRepository.findByUserId(userId)
                .orElse(new CompanionPreference());

        modelMapper.map(dto, preference);
        preference.setUserId(userId);

        CompanionPreference saved  = companionPreferenceRepository.save(preference);
        log.info("Companion preferences saved for userId: {}",userId);

        return modelMapper.map(saved, CompanionPreferenceDto.class);
    }


    public CompanionPreferenceDto getPreference(){
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Fetching preference for userId: {}",userId);

        CompanionPreference preference = companionPreferenceRepository.findByUserId(userId)
                .orElseThrow(()->new ResourceNotFoundException("CompanionPreference not found with userId ",userId));

        return modelMapper.map(preference, CompanionPreferenceDto.class);
    }
}
