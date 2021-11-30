package com.cis.rockon.controller;

import com.cis.rockon.exceptions.UserNotFoundException;
import com.cis.rockon.model.User;
import com.cis.rockon.repository.UserRepository;
import org.hibernate.PropertyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("")
    public ResponseEntity<User> createNewUser(@RequestBody User newUser) {
        try {
            newUser.setId(null);
            User user = repository.save(newUser);
            return ResponseEntity.ok().body(user);

            /* if the posted data is missing values that are required
             * or if we have a unique constraint violation */
        } catch (PropertyValueException | DataIntegrityViolationException | IllegalStateException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id)
            throws UserNotFoundException {

        User user = repository
                .findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("Unable to find user " + id)
                );

        return ResponseEntity.ok().body(user);
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> users = repository.findAll();
        return ResponseEntity.ok(users);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        if (!repository.existsById(id))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {

            if (!repository.existsById(id))
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();

            User user = repository.save(updatedUser);

            return ResponseEntity.ok().body(user);

            /* if the posted data is missing values that are required
             * or if we have a unique constraint violation */
        } catch (PropertyValueException | DataIntegrityViolationException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @PutMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, Object> payload)
            // no security here :)
            throws UserNotFoundException {

            String email = (String) payload.get("email");

            User user = repository.findOneByEmail(email)
                    .orElseThrow(() ->
                            new UserNotFoundException("Unable to find user with email " + email)
                    );

            return ResponseEntity.ok().body(user);
    }
}
