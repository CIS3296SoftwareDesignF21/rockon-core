package com.cis.rockon.controller;


import com.cis.rockon.model.User;
import com.cis.rockon.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DataNeo4jTest
class UserControllerTest {

    private final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    UserController controller;

    @Autowired
    UserRepository repository;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    private User user;

    public UserControllerTest() {
        mapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    void setUp() {

        mapper.setTimeZone(TimeZone.getTimeZone("EST"));

        user = new User()
                .setId(1L)
                .setFirstName("Hooter T.")
                .setLastName("Owl")
                .setPhoneNumber("215 420 6969")
                .setEmail("cis@temple.edu")
                .setBirthday(LocalDate.ofEpochDay(0))
                .setBiography("I'm just here to test!")
                .setYearsOfExperience(69)
                .setLastSeenLocation(new double[]{0.0, 0.0})
                .setSearchRadius(1)
                .setTypeSportClimbing(true)
                .setTypeFreeSolo(true)
                .setTypeTopRope(false)
                .setTypeFreeSolo(false)
                .setTypeBouldering(null);
    }

    @Test
    @DirtiesContext
    public void createNewUser() throws Exception {
        String result = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        User user = mapper.readValue(result, User.class);

        assertEquals(this.user, user);
    }

    @Test
    @DirtiesContext
    public void createUserWithMissingData() throws Exception {
        User badUser = new User()
                .setFirstName("Richard")
                .setLastName("Englert");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(badUser)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DirtiesContext
    public void createUserWithSameEmail() throws Exception {

        /* email is the only @unique property of users, so we should never have a case where two entities
         * can have the same email! */
        repository.save(user);

        user.setId(2L);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DirtiesContext
    public void deleteUserExists() throws Exception {

        user = repository.save(user);

        mockMvc.perform(delete("/api/users/" + user.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DirtiesContext
    public void deleteUserNotExists() throws Exception {
        mockMvc.perform(delete("/api/users/69"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext
    public void getUserExists() throws Exception {

        user = repository.save(user);

        String result = mockMvc.perform(get("/api/users/" + user.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        user = mapper.readValue(result, User.class);

        assertEquals(this.user, user);
    }

    @Test
    public void getUserNotExists() throws Exception {

        mockMvc.perform(get("/api/users/69"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void getAllUsersNoneExists() throws Exception {

        String result = mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<User> users = Arrays.asList(mapper.readValue(result, User[].class));
        assertEquals(users.size(), 0);

    }

    @Test
    @DirtiesContext
    public void getAllUsers() throws Exception {

        User[] mockUsers = {
                new User()
                        .setId(1L).setFirstName("Hooter T.").setLastName("Owl").setPhoneNumber("215 420 6969")
                        .setEmail("cis1@temple.edu").setBirthday(LocalDate.ofEpochDay(0)).setLastSeenLocation(new double[]{0.0, 0.0})
                        .setBiography("I'm just here to test!").setYearsOfExperience(69).setTypeSportClimbing(true)
                        .setTypeFreeSolo(true).setTypeTopRope(false).setTypeFreeSolo(false).setTypeBouldering(null)
                        .setSearchRadius(1),
                new User()
                        .setId(2L).setFirstName("Hooter T.").setLastName("Owl").setPhoneNumber("215 420 6969")
                        .setEmail("cis2@temple.edu").setBirthday(LocalDate.ofEpochDay(0)).setLastSeenLocation(new double[]{0.0, 0.0})
                        .setBiography("I'm just here to test!").setYearsOfExperience(69).setTypeSportClimbing(true)
                        .setTypeFreeSolo(true).setTypeTopRope(false).setTypeFreeSolo(false).setTypeBouldering(null)
                        .setSearchRadius(1),
                new User()
                        .setId(3L).setFirstName("Hooter T.").setLastName("Owl").setPhoneNumber("215 420 6969")
                        .setEmail("cis3@temple.edu").setBirthday(LocalDate.ofEpochDay(0)).setLastSeenLocation(new double[]{0.0, 0.0})
                        .setBiography("I'm just here to test!").setYearsOfExperience(69).setTypeSportClimbing(true)
                        .setTypeFreeSolo(true).setTypeTopRope(false).setTypeFreeSolo(false).setTypeBouldering(null)
                        .setSearchRadius(1)
        };

        repository.saveAll(Arrays.asList(mockUsers));

        String result = mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<User> actual = Arrays.asList(mapper.readValue(result, User[].class));
        assertEquals(3, actual.size());
        assertThat(actual, containsInAnyOrder(mockUsers));
    }

    @Test
    public void updateUserNotExists() throws Exception {

        mockMvc.perform(put("/api/users/69")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    @DirtiesContext
    public void updateUser() throws Exception {

        user = repository.save(user);
        user.setFirstName("Gritty");
        user.setYearsOfExperience(420);

        String result = mockMvc.perform(put("/api/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        User actual = mapper.readValue(result, User.class);
        assertEquals(actual, user);
    }

}
