package com.company.SafarSaathi.trip_service.dtos;


import com.company.SafarSaathi.trip_service.enums.ModeOfTravel;
import com.company.SafarSaathi.trip_service.enums.TripStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TripUpdateRequestDto {

    private String destination;
    private String origin;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ModeOfTravel modeOfTravel;
    private Integer maxTravelers;
    private String description;

    private boolean isPrivate;
    private Double estimatedCost;
    private TripStatus status;
}
