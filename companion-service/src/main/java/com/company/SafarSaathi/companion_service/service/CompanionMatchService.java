package com.company.SafarSaathi.companion_service.service;


import com.company.SafarSaathi.companion_service.client.MatchingClient;
import com.company.SafarSaathi.companion_service.client.TripClient;
import com.company.SafarSaathi.companion_service.client.UserClient;
import com.company.SafarSaathi.companion_service.repository.CompanionPreferenceRepository;
import com.company.SafarSaathi.companion_service.repository.CompanionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanionMatchService {

    private final CompanionRepository companionRepository;
    private final CompanionPreferenceRepository preferenceRepository;
    private final ModelMapper modelMapper;
    private final MatchingClient matchingClient;
    private final TripClient tripClient;
    private UserClient userClient;
}
