package com.company.SafarSaathi.companion_service.neo4j_entities;


import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

@RelationshipProperties
public class CompanionRequestRel {


    @Id
    @GeneratedValue
    private Long id;

    private String status;

    private UserNode toUser;
}
