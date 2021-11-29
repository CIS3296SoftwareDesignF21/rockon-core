package com.cis.rockon.repository;

import com.cis.rockon.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends Neo4jRepository<User, Long> {

    @Query("MATCH (u:User) WHERE ID(u) = user\n" +
           "MATCH (u)<-[:CONNECTIONS]-(matches) \n" +
           "WITH collect(matches) as incoming\n" +
           "MATCH (u)-[:CONNECTIONS]->(matches)\n" +
           "WHERE matches IN incoming\n" +
           "RETURN matches")
    List<User> getConnections(@Param("user") Long id);

    @Query("MATCH (u) WHERE ID(u) = user\n" +
           "MATCH (u)<-[:CONNECTIONS]-(swipes)\n" +
           "RETURN swipes")
    List<User> getSwipedOnBy(@Param("user") Long id);

    @Query("MATCH (u) WHERE ID(u) = user\n" +
           "MATCH (u)-[:CONNECTIONS]->(swipes)\n" +
           "RETURN swipes")
    List<User> getSwipedOn(@Param("user") Long id);

}
