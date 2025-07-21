package com.company.SafarSaathi.companion_service.controller;


import com.company.SafarSaathi.companion_service.auth.UserContextHolder;
import com.company.SafarSaathi.companion_service.dtos.CompanionDto;
import com.company.SafarSaathi.companion_service.dtos.CompanionPreferenceDto;
import com.company.SafarSaathi.companion_service.dtos.CreateCompanionRequest;
import com.company.SafarSaathi.companion_service.service.CompanionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
public class CompanionController {

    private final CompanionService companionService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<CompanionDto> createCompanion(@RequestBody CompanionDto dto){
        log.info("Creating companion request for user");
        return ResponseEntity.ok(companionService.createCompanion(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanionDto> updateCompanion(@PathVariable Long id,
                                                        @RequestBody CompanionDto dto){
        log.info("updating companion with id: {}",id);
        return ResponseEntity.ok(companionService.updateCompanion(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompanion(@PathVariable Long id){
        log.info("Deleting companion with id: {}", id);
        companionService.deleteCompanion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CompanionDto>> getAllCompanions(){
        log.info("Getting all the companion");
        return ResponseEntity.ok(companionService.getAllCompanions());
    }

    @PostMapping("/preferences")
    public ResponseEntity<CompanionPreferenceDto> setPreference(@RequestBody CompanionPreferenceDto dto){
        log.info("setting  the companionPreference:");
        return ResponseEntity.ok(companionService.createOrUpdatePreference(dto));
    }

    @GetMapping("/me")
    public ResponseEntity<CompanionPreferenceDto> getPreferences(){
        log.info("Getting all the preferences: ");
        return ResponseEntity.ok(companionService.getPreference());
    }

}
