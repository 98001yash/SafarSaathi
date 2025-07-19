package com.company.SafarSaathi.trip_service.entity;


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
    private String origin;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String modeOfTravel;  // e.g. Bus, train, Flight

    private Integer maxTravelers;
    private Integer currentTravelers;

    private String description;

    private boolean isPrivate;
    private Double estimatedCost;

    private String status; // e.g. PLANNED, ONGOING, COMPLETED, CANCELLED
    private Long userId; // Fetched from the API-GATEWAY (X-user-Id_


}
