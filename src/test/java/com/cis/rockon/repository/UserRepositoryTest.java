package com.cis.rockon.repository;

import com.cis.rockon.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Arrays;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    private static final String PASSWORD = "foobar";

    @Container
    private static final Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>("neo4j:" + env())
            .withAdminPassword(PASSWORD);

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", neo4jContainer::getBoltUrl);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", () -> PASSWORD);
    }

    @BeforeEach
    void setup(@Autowired Driver driver, @Autowired UserRepository repository) {

        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH (n) DETACH DELETE n"); // clear all nodes and connections
                return null;
            });
        }

        User[] users = new User[]{
                new User()
                        .setFirstName("Hooter T.").setLastName("Owl")
                        .setPhoneNumber("215 420 6969").setEmail("cis1@temple.edu")
                        .setBirthday(LocalDate.ofEpochDay(0)).setBiography("I'm just here to test!").setYearsOfExperience(69)
                        .setLastSeenLocation(new double[]{39.981996346936, -75.153041291542}) // SERC
                        .setSearchRadius(25 /* km */).setTypeSportClimbing(true).setTypeFreeSolo(true)
                        .setTypeTopRope(false).setTypeFreeSolo(false).setTypeBouldering(null),
                new User()
                        .setFirstName("Stella D.").setLastName("Owl")
                        .setPhoneNumber("215 420 6969").setEmail("cis2@temple.edu")
                        .setBirthday(LocalDate.ofEpochDay(0)).setBiography("I'm just here to test!").setYearsOfExperience(420)
                        .setLastSeenLocation(new double[]{39.981348547211, -75.154349633751}) // Bell Tower
                        .setSearchRadius(25 /* km */).setTypeSportClimbing(true).setTypeFreeSolo(true)
                        .setTypeTopRope(false).setTypeFreeSolo(false).setTypeBouldering(null),
                new User()
                        .setFirstName("Jason").setLastName("Wingard")
                        .setPhoneNumber("215 420 6969").setEmail("president@temple.edu")
                        .setBirthday(LocalDate.ofEpochDay(0)).setBiography("I'm just here to test!").setYearsOfExperience(69)
                        .setLastSeenLocation(new double[]{39.9806376631842, -75.154914261904}) // Tuttleman
                        .setSearchRadius(25 /* km */).setTypeSportClimbing(true).setTypeFreeSolo(true)
                        .setTypeTopRope(false).setTypeFreeSolo(true).setTypeBouldering(null),
                new User()
                        .setFirstName("Dick").setLastName("Cheney")
                        .setPhoneNumber("215 420 6969").setEmail("dick@temple.edu")
                        .setBirthday(LocalDate.ofEpochDay(0)).setBiography("I'm just here to test!").setYearsOfExperience(69)
                        .setLastSeenLocation(new double[]{38.8976579135955, -77.036553189077}) // White House
                        .setSearchRadius(25 /* km */).setTypeSportClimbing(true).setTypeFreeSolo(true)
                        .setTypeTopRope(false).setTypeFreeSolo(false).setTypeBouldering(null)
        };

        repository.saveAll(Arrays.asList(users));
    }

    @Test
    void getAllConnections(@Autowired UserRepository repository) {
    }

    @Test
    void getAllSwipes(@Autowired UserRepository repository) {
    }

    @Test
    void getAllSwipedOnBy(@Autowired UserRepository repository) {
    }

    private static String env() {
        String value = System.getenv("NEO4J_VERSION");
        if (value == null) {
            return "4.3";
        }
        return value;
    }
}
