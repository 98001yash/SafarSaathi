package com.company.SafarSaathi.companion_service.neo4j_entities;


import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("User")
public class UserNode {

    @Id
    private Long id;

    private String name;
}
