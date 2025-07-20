package com.company.SafarSaathi.trip_service.entity;


import com.company.SafarSaathi.trip_service.enums.ModeOfTravel;
import com.company.SafarSaathi.trip_service.enums.TripStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "trips")
public class Trip {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destination;
    private Double destinationLat;
    private Double destinationLng;


    private String origin;
    private Double originLat;
    private Double originLng;


    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private ModeOfTravel modeOfTravel;  // e.g. Bus, train, Flight

    private Integer maxTravelers;
    private Integer currentTravelers;

    private String description;

    private boolean isPrivate;
    private Double estimatedCost;


    @Enumerated(EnumType.STRING)
    private TripStatus status; // e.g. PLANNED, ONGOING, COMPLETED, CANCELLED
    private Long userId; // Fetched from the API-GATEWAY (X-user-Id_


}
