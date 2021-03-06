package com.cis.rockon.controller;

import com.cis.rockon.model.User;
import com.cis.rockon.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
public class UserQueueControllerTest {

    private final Logger logger = LoggerFactory.getLogger(UserQueueControllerTest.class);

    private static final String PASSWORD = "foobar";

    @Container
    private static final Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>("neo4j:" + env())
            .withAdminPassword(PASSWORD);

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", neo4jContainer::getBoltUrl);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", () -> PASSWORD);
        registry.add("spring.data.neo4j.database", () -> "neo4j");
    }

    @Autowired
    UserQueueController controller;

    private final User[] testUsers = new User[]{
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

    public UserQueueControllerTest() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DirtiesContext
    public void swipeByUserNotExists(@Autowired UserRepository repository, @Autowired MockMvc mockMvc) throws Exception {
        testUsers[0] = repository.save(testUsers[0]);

        // user[0] is being swiped on
        mockMvc.perform(get("/api/user-queue/swipe/420/yes")
                        .param("user", Long.toString(testUsers[0].getId())))
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext
    public void swipeOnUserNotExists(@Autowired UserRepository repository, @Autowired MockMvc mockMvc) throws Exception {
        testUsers[0] = repository.save(testUsers[0]);

        // user[0] is swiping
        mockMvc.perform(get("/api/user-queue/swipe/" + testUsers[0].getId() + "/yes")
                        .param("user", "420"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext
    public void swipeOnUser(@Autowired UserRepository repository, @Autowired MockMvc mockMvc) throws Exception {

        repository.saveAll(Arrays.asList(testUsers));

        // user[0] is swiping on user[1]
        mockMvc.perform(get("/api/user-queue/swipe/" + testUsers[0].getId() +"/yes")
                .param("user", Long.toString(testUsers[1].getId())))
                .andExpect(status().isNoContent());

        assertTrue(repository.getSwipedOn(testUsers[0].getId()).contains(testUsers[1]));

        // user[1] is swiping on user[0]
        mockMvc.perform(get("/api/user-queue/swipe/" + testUsers[1].getId() + "/yes")
                        .param("user", Long.toString(testUsers[0].getId())))
                .andExpect(status().isOk());

        User[] users = repository.findAll().toArray(new User[0]);

        assertTrue(repository.getSwipedOn(users[1].getId()).contains(users[0]));

        assertTrue(users[0].getConnections().contains(users[1]));
        assertTrue(users[1].getConnections().contains(users[0]));
    }

    private static String env() {
        String value = System.getenv("NEO4J_VERSION");
        if (value == null) {
            return "4.3";
        }
        return value;
    }
}
