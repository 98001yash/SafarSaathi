package com.company.SafarSaathi.companion_service.service;

import com.company.SafarSaathi.companion_service.entity.CompanionPreference;
import com.company.SafarSaathi.companion_service.repository.CompanionPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanionPreferenceService {

    private final CompanionPreferenceRepository preferenceRepository;

    public void savePreference(CompanionPreference preference) {
        preferenceRepository.save(preference);
    }

    public Optional<CompanionPreference> getPreferenceByUserId(Long userId) {
        return preferenceRepository.findByUserId(userId);
    }
}