package com.cis.rockon;

import com.cis.rockon.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class ApplicationTests {

    @Autowired
    UserController controller;

    @Test
    void contextLoads() {
        assertNotNull(controller);
    }

}
