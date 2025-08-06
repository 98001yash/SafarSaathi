package com.company.SafarSaathi.companion_service.controller;


import com.company.SafarSaathi.companion_service.auth.UserContextHolder;
import com.company.SafarSaathi.companion_service.dtos.*;
import com.company.SafarSaathi.companion_service.entity.CompanionPreference;
import com.company.SafarSaathi.companion_service.service.CompanionPreferenceService;
import com.company.SafarSaathi.companion_service.service.CompanionRequestService;
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
    private final CompanionRequestService companionRequestService;

    @PostMapping("/create")
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

    @PostMapping("/preferences")
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


    // code fpr the companion Request controller


    @PostMapping("/send")
    public ResponseEntity<CompanionRequestResponseDto> sendRequest(@RequestBody CompanionRequestDto dto){
        Long currentUserId = UserContextHolder.getCurrentUserId();
        dto.setSenderId(currentUserId);
        log.info("User {} sending a companion request to user: {}",currentUserId, dto.getReceiverId());
        return ResponseEntity.ok(companionRequestService.sendRequest(dto));
    }

    @PostMapping("/{requestId}/accept")
    public ResponseEntity<CompanionRequestResponseDto> acceptRequest(@PathVariable Long requestId){
        Long currentUserId = UserContextHolder.getCurrentUserId();

        log.info("User {} is accepting request ID {}",currentUserId, requestId);

        return ResponseEntity.ok(companionRequestService.acceptRequest(requestId));
    }

    // API Added for the reject Request
    @PostMapping("/{requestId}/reject")
    public ResponseEntity<CompanionRequestResponseDto> rejectRequest(@PathVariable Long requestId){
        Long currentUserId = UserContextHolder.getCurrentUserId();
        log.info("User {} is rejecting request ID: {}",currentUserId, requestId);
        return ResponseEntity.ok(companionRequestService.rejectRequest(requestId));
    }

    @GetMapping("/received")
    public ResponseEntity<List<CompanionRequestResponseDto>> getReceivedRequests() {
        Long currentUserId = UserContextHolder.getCurrentUserId();
        log.info("Fetching received companion requests for user {}", currentUserId);

        return ResponseEntity.ok(companionRequestService.getRequestsForUser());
    }

    // API for the getSentRequests
    @GetMapping("/sent")
    public ResponseEntity<List<CompanionRequestResponseDto>> getSentRequests() {
        Long currentUserId = UserContextHolder.getCurrentUserId();
        log.info("Fetching sent companion requests by user {}", currentUserId);

        return ResponseEntity.ok(companionRequestService.getSentRequests());
    }

}
