package com.company.SafarSaathi.matching_service.dtos;


import com.company.SafarSaathi.matching_service.enums.ModeOfTravel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TripDto {

    private Long id;
    private String destination;
    private String origin;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private ModeOfTravel modeOfTravel;

    private int maxTravelers;
    private int currentTravelers;

    private String description;
    private Double estimatedCost;
    private String status;
    private Long userId;
    private boolean isPrivate;
}
