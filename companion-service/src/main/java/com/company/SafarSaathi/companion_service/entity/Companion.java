package com.company.SafarSaathi.companion_service.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "companions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Companion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tripId;
    private Long userId;


    private String status;
    private String message;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "companion_matches", joinColumns = @JoinColumn(name = "companion_id"))
    @Column(name = "matched_user_id")
    private Set<Long> matchedUserIds = new HashSet<>();
}
