package com.company.SafarSaathi.companion_service.neo4j_entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDateTime;

@RelationshipProperties
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanionRequestRelation {
    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private UserNode receiver;

    private Long tripId;
    private LocalDateTime timeStamp;
    private String status;
}