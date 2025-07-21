package com.company.SafarSaathi.companion_service.repository;

import com.company.SafarSaathi.companion_service.entity.CompanionPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanionPreferenceRepository extends JpaRepository<CompanionPreference,Long> {


    Optional<CompanionPreference>  findByUserId(Long userId);
}
