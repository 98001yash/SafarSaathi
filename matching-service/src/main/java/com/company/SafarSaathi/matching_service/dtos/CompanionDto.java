package com.company.SafarSaathi.matching_service.dtos;


import lombok.Data;

import java.util.Set;

@Data
public class CompanionDto {

    private Long id;
    private Long userId;
    private Long tripId;
    private String status;
    private String message;

    private Set<Long> matchedUserIds;
}
