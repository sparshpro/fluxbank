package com.fluxbank.corebankingservice.user.repository;

import com.fluxbank.corebankingservice.user.entity.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    // SAVE USER

    @Test
    @DisplayName("Should save user successfully")
    void shouldSaveUser() {

        User user = new User();
        user.setName("John");
        user.setEmail("john@test.com");
        user.setPhone("9999999999");

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals("John", savedUser.getName());
    }

    // FIND USER BY ID

    @Test
    @DisplayName("Should find user by id")
    void shouldFindUserById() {

        User user = new User();
        user.setName("John");
        user.setEmail("john@test.com");
        user.setPhone("9999999999");

        User savedUser = userRepository.save(user);

        Optional<User> foundUser =
                userRepository.findById(savedUser.getId());

        assertTrue(foundUser.isPresent());
        assertEquals("John", foundUser.get().getName());
    }

    // FIND ALL USERS

    @Test
    @DisplayName("Should return all users")
    void shouldFindAllUsers() {

        User user1 = new User();
        user1.setName("John");
        user1.setEmail("john@test.com");
        user1.setPhone("9999999999");

        User user2 = new User();
        user2.setName("Jane");
        user2.setEmail("jane@test.com");
        user2.setPhone("8888888888");

        userRepository.save(user1);
        userRepository.save(user2);

        var users = userRepository.findAll();

        assertEquals(2, users.size());
    }

    // DELETE USER

    @Test
    @DisplayName("Should delete user")
    void shouldDeleteUser() {

        User user = new User();
        user.setName("John");
        user.setEmail("john@test.com");
        user.setPhone("9999999999");

        User savedUser = userRepository.save(user);

        userRepository.deleteById(savedUser.getId());

        Optional<User> deletedUser =
                userRepository.findById(savedUser.getId());

        assertFalse(deletedUser.isPresent());
    }
}