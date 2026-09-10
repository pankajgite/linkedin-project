package com.pankaj.linkedinProject.ConnectionsService.repository;

import com.pankaj.linkedinProject.ConnectionsService.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Optional<Person> findByUserId(Long userId);

    @Query("match (pa:Person) -[:CONNECTED_TO]- (pb:Person) " +
            "where pa.id= $userId " +
            "return pb")
    List<Person> getFirstDegreeConnections(Long userId);
}
