package com.company.SafarSaathi.companion_service.service;


import com.company.SafarSaathi.companion_service.auth.UserContextHolder;
import com.company.SafarSaathi.companion_service.dtos.CompanionDto;
import com.company.SafarSaathi.companion_service.entity.Companion;
import com.company.SafarSaathi.companion_service.repository.CompanionPreferenceRepository;
import com.company.SafarSaathi.companion_service.repository.CompanionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
