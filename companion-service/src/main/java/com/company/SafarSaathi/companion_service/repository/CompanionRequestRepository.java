package com.company.SafarSaathi.companion_service.repository;

import com.company.SafarSaathi.companion_service.entity.CompanionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanionRequestRepository extends JpaRepository<CompanionRequest,Long> {


    List<CompanionRequest> findByReceiverId(Long receiverId);

    List<CompanionRequest> findBySenderId(Long senderId);

    Optional<CompanionRequest> findBySenderIdAndReceiverIdAndTripId(Long senderId, Long receiverId, Long tripId);

}
