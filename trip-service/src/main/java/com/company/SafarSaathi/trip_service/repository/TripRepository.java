package com.company.SafarSaathi.trip_service.repository;

import com.company.SafarSaathi.trip_service.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip,Long> {

    List<Trip> findByUserId(Long userId);

    List<Trip> findByIsPrivateFalse();
    List<Trip> findByStatus(String status);
}
