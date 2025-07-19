package com.company.SafarSaathi.trip_service.dtos;


import com.company.SafarSaathi.trip_service.enums.ModeOfTravel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TripCreateRequestDto {

    private String destination;
    private String origin;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ModeOfTravel modeOfTravel;

    private Integer maxTravelers;

    private String description;
    private boolean isPrivate;
    private Double estimatedCost;
}
