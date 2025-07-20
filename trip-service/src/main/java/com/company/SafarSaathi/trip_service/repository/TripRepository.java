package com.company.SafarSaathi.trip_service.repository;

import com.company.SafarSaathi.trip_service.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip,Long>, JpaSpecificationExecutor<Trip> {

    List<Trip> findByUserId(Long userId);

    List<Trip> findByIsPrivateFalse();
    List<Trip> findByStatus(String status);
}
