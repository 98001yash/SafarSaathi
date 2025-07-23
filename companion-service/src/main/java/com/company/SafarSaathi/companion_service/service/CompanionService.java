package com.company.SafarSaathi.companion_service.service;


import com.company.SafarSaathi.companion_service.auth.UserContextHolder;
import com.company.SafarSaathi.companion_service.client.TripClient;
import com.company.SafarSaathi.companion_service.client.UserClient;
import com.company.SafarSaathi.companion_service.dtos.*;
import com.company.SafarSaathi.companion_service.entity.Companion;
import com.company.SafarSaathi.companion_service.entity.CompanionPreference;
import com.company.SafarSaathi.companion_service.enums.ModeOfTravel;
import com.company.SafarSaathi.companion_service.exceptions.BadRequestException;
import com.company.SafarSaathi.companion_service.exceptions.ResourceNotFoundException;
import com.company.SafarSaathi.companion_service.repository.CompanionPreferenceRepository;
import com.company.SafarSaathi.companion_service.repository.CompanionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanionService {


    private final CompanionRepository companionRepository;
    private final CompanionPreferenceRepository companionPreferenceRepository;
    private final ModelMapper modelMapper;
    private final UserClient userClient;

    private final TripClient tripClient;

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
                .orElseThrow(()->new ResourceNotFoundException("CompanionPreference not found with userId "+userId));
        return modelMapper.map(preference, CompanionPreferenceDto.class);
    }

    @Transactional
    public void matchCompanions() {
        log.info("🔁 Starting companion matching process...");

        List<Companion> open = companionRepository.findByStatus("OPEN");
        log.info("Found {} open companions", open.size());

        for (int i = 0; i < open.size(); i++) {
            Companion a = open.get(i);
            CompanionPreference prefA = companionPreferenceRepository.findByUserId(a.getUserId()).orElse(null);
            if (prefA == null) {
                log.warn("❌ No preferences found for userId: {}", a.getUserId());
                continue;
            }

            for (int j = i + 1; j < open.size(); j++) {
                Companion b = open.get(j);
                if (a.getUserId().equals(b.getUserId())) continue;

                CompanionPreference prefB = companionPreferenceRepository.findByUserId(b.getUserId()).orElse(null);
                if (prefB == null) {
                    log.warn("❌ No preferences found for userId: {}", b.getUserId());
                    continue;
                }

                log.info("🔍 Checking compatibility between user {} and {}", a.getUserId(), b.getUserId());

                if (compatible(a, prefA, b, prefB)) {
                    log.info("✅ Matched: {} <--> {}", a.getUserId(), b.getUserId());
                    a.getMatchedUserIds().add(b.getUserId());
                    b.getMatchedUserIds().add(a.getUserId());
                } else {
                    log.info("❌ No match between {} and {}", a.getUserId(), b.getUserId());
                }
            }
        }

        companionRepository.saveAll(open);
        log.info("💾 Saved matched users to DB");
    }

    public List<CompanionDto> getMatchedForCurrentUser() {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("📥 Fetching matched companions for userId: {}", userId);

        List<Companion> found = companionRepository.findByMatchedUserIdsContains(userId);
        return found.stream()
                .map(c -> modelMapper.map(c, CompanionDto.class))
                .toList();
    }

    private boolean compatible(Companion a, CompanionPreference pA,
                               Companion b, CompanionPreference pB) {
        try {
            UserProfile userA = userClient.getUserProfile(a.getUserId());
            UserProfile userB = userClient.getUserProfile(b.getUserId());

            if (userA == null || userB == null) {
                log.warn("⚠️ User profile missing: A={} or B={}", a.getUserId(), b.getUserId());
                return false;
            }

            boolean aMatchesB = matchesPreference(userA, pB, b.getTripId(), a.getTripId());
            boolean bMatchesA = matchesPreference(userB, pA, a.getTripId(), b.getTripId());

            return aMatchesB && bMatchesA;

        } catch (Exception e) {
            log.error("❌ Error while matching users {} and {}: {}", a.getUserId(), b.getUserId(), e.getMessage(), e);
            return false;
        }
    }

    private boolean matchesPreference(UserProfile user, CompanionPreference preference, Long targetTripId, Long userTripId) {
        if (user.getAge() < preference.getPreferredAgeMin() || user.getAge() > preference.getPreferredAgeMax()) return false;
        if (preference.getPreferredGender() != null &&
                !preference.getPreferredGender().equalsIgnoreCase(user.getGender())) return false;
        if (!Boolean.TRUE.equals(preference.getAllowSmokers()) && user.isSmoker()) return false;
        if (!Boolean.TRUE.equals(preference.getAllowDrinkers()) && user.isDrinker()) return false;

        ModeOfTravel travelType = getTripMode(targetTripId);
        if (preference.getTravelType() != null && travelType != null &&
                !preference.getTravelType().equals(travelType)) return false;

        // Optional: same trip check (you can disable this if not required)
        return Objects.equals(targetTripId, userTripId);
    }

    private ModeOfTravel getTripMode(Long tripId) {
        try {
            TripDto trip = tripClient.getTripById(tripId);
            if (trip == null) {
                log.warn("⚠️ Trip not found for id {}", tripId);
                return null;
            }
            return trip.getModeOfTravel();
        } catch (Exception e) {
            log.error("❌ Failed to fetch trip with ID: {}", tripId, e);
            return null;
        }
    }
}
