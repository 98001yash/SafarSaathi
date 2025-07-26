package com.company.SafarSaathi.companion_service.controller;


import com.company.SafarSaathi.companion_service.dtos.CompanionDto;
import com.company.SafarSaathi.companion_service.dtos.CreateCompanionRequest;
import com.company.SafarSaathi.companion_service.dtos.UpdateCompanionRequest;
import com.company.SafarSaathi.companion_service.entity.CompanionPreference;
import com.company.SafarSaathi.companion_service.service.CompanionPreferenceService;
import com.company.SafarSaathi.companion_service.service.CompanionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
public class CompanionController {

    private final CompanionService companionService;
    private final CompanionPreferenceService preferenceService;

    @PostMapping
    public ResponseEntity<CompanionDto> createCompanion(@RequestBody CreateCompanionRequest requestDto) {
        CompanionDto created = companionService.createCompanion(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanionDto> updateCompanion(
            @PathVariable Long id,
            @RequestBody UpdateCompanionRequest request
    ) {
        CompanionDto updated = companionService.updateCompanion(id, request);
        return ResponseEntity.ok(updated);
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

    @PostMapping
    public ResponseEntity<String> savePreference(@RequestBody CompanionPreference preference) {
        preferenceService.savePreference(preference);
        return ResponseEntity.ok("Preference saved successfully");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CompanionPreference> getPreference(@PathVariable Long userId) {
        return preferenceService.getPreferenceByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
