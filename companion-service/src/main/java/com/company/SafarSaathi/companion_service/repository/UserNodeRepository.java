package com.company.SafarSaathi.companion_service.repository;

import com.company.SafarSaathi.companion_service.neo4j_entities.UserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface  UserNodeRepository extends Neo4jRepository<UserNode,Long> {

}
