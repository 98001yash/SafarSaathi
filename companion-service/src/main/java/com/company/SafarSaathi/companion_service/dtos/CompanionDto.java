package com.company.SafarSaathi.companion_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanionDto {

    private Long id;
    private Long tripId;
    private Long userId;

    private String status;

    private String message;

    private Set<Long> matchedUserIds;
}
