package com.cis.rockon.repository;

import com.cis.rockon.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends Neo4jRepository<User, Long> {

    @Query("MATCH (connection:User)-[:CONNECTIONS] RETURN connection")
    List<User> getConnections(@Param("u") User u);

    @Query("MATCH (u:User)<-[:CONNECTIONS] RETURN u")
    List<User> getSwipedOn(@Param("userId") Long userId);

    @Query("MATCH (u:User)->[:CONNECTIONS] RETURN u")
    List<User> getSwipedOnBy(@Param("userId") Long userId);

}
