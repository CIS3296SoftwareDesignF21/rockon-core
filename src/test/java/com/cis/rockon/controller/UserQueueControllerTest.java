package com.cis.rockon.controller;

import com.cis.rockon.model.Location;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cis.rockon.model.User;
import com.cis.rockon.repository.UserRepository;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.internal.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DataNeo4jTest
public class UserQueueControllerTest {

    private final Logger logger = LoggerFactory.getLogger(UserQueueController.class);

    @Autowired
    UserQueueController controller;

    @Autowired
    UserRepository repository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    private User[] users;

    public UserQueueControllerTest() {
        mapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    public void setUp() {
        users = new User[]{
                new User()
                        .setFirstName("Hooter T.").setLastName("Owl")
                        .setPhoneNumber("215 420 6969").setEmail("cis1@temple.edu")
                        .setBirthday(LocalDate.ofEpochDay(0)).setBiography("I'm just here to test!").setYearsOfExperience(69)
                        .setLastSeenLocation(new Location(39.981996346936, -75.153041291542)) // SERC
                        .setSearchRadius(25 /* km */).setTypeSportClimbing(true).setTypeFreeSolo(true)
                        .setTypeTopRope(false).setTypeFreeSolo(false).setTypeBouldering(null),
                new User()
                        .setFirstName("Stella D.").setLastName("Owl")
                        .setPhoneNumber("215 420 6969").setEmail("cis2@temple.edu")
                        .setBirthday(LocalDate.ofEpochDay(0)).setBiography("I'm just here to test!").setYearsOfExperience(420)
                        .setLastSeenLocation(new Location(39.981348547211, -75.154349633751)) // Bell Tower
                        .setSearchRadius(25 /* km */).setTypeSportClimbing(true).setTypeFreeSolo(true)
                        .setTypeTopRope(false).setTypeFreeSolo(false).setTypeBouldering(null),
                new User()
                        .setFirstName("Jason").setLastName("Wingard")
                        .setPhoneNumber("215 420 6969").setEmail("president@temple.edu")
                        .setBirthday(LocalDate.ofEpochDay(0)).setBiography("I'm just here to test!").setYearsOfExperience(69)
                        .setLastSeenLocation(new Location(39.9806376631842, -75.154914261904)) // Tuttleman
                        .setSearchRadius(25 /* km */).setTypeSportClimbing(true).setTypeFreeSolo(true)
                        .setTypeTopRope(false).setTypeFreeSolo(true).setTypeBouldering(null),
                new User()
                        .setFirstName("Dick").setLastName("Cheney")
                        .setPhoneNumber("215 420 6969").setEmail("dick@temple.edu")
                        .setBirthday(LocalDate.ofEpochDay(0)).setBiography("I'm just here to test!").setYearsOfExperience(69)
                        .setLastSeenLocation(new Location(38.8976579135955, -77.036553189077)) // White House
                        .setSearchRadius(25 /* km */).setTypeSportClimbing(true).setTypeFreeSolo(true)
                        .setTypeTopRope(false).setTypeFreeSolo(false).setTypeBouldering(null)
        };
    }

    @Test
    @DirtiesContext
    public void swipeByUserNotExists() throws Exception {
        users[0] = repository.save(users[0]);

        // user[0] is being swiped on
        mockMvc.perform(get("/api/user-queue/swipe/420")
                        .param("user", Long.toString(users[0].getId())))
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext
    public void swipeOnUserNotExists() throws Exception {
        users[0] = repository.save(users[0]);

        // user[0] is swiping
        mockMvc.perform(get("/api/user-queue/swipe/" + users[0].getId())
                        .param("user", "420"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext
    @Disabled
    public void swipeOnUser() throws Exception {
        repository.saveAll(Arrays.asList(users));

        // user[0] is swiping on user[1]
        mockMvc.perform(get("/api/user-queue/swipe/" + users[0].getId())
                .param("user", Long.toString(users[1].getId())))
                .andExpect(status().isNoContent());

        users = repository.findAll().toArray(new User[0]);

        assertTrue(users[0].getSwipedOn().contains(users[1]));

        System.out.println("-----");
        // user[1] is swiping on user[0]
        mockMvc.perform(get("/api/user-queue/swipe/" + users[1].getId())
                        .param("user", Long.toString(users[0].getId())))
                .andExpect(status().isOk());

        users = repository.findAll().toArray(new User[0]);

        assertTrue(users[1].getSwipedOn().contains(users[0]));

        assertTrue(users[0].getConnections().contains(users[1]));
        assertTrue(users[1].getConnections().contains(users[0]));
    }
}
