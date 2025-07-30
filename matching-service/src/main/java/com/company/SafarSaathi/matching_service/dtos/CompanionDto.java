package com.company.SafarSaathi.matching_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanionDto {

    private Long id;
    private Long userId;
    private Long tripId;
    private String status;
    private String message;

    private Set<Long> matchedUserIds;
}
