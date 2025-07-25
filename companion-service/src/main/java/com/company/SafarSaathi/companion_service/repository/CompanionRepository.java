package com.company.SafarSaathi.companion_service.repository;

import com.company.SafarSaathi.companion_service.entity.Companion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanionRepository extends JpaRepository<Companion,Long> {

    List<Companion> findByTripId(Long tripId);

    List<Companion> findByUserId(Long userId);

    List<Companion> findByStatus(String open);

}
