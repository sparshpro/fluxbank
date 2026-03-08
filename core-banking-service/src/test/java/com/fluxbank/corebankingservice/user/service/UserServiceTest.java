package com.fluxbank.corebankingservice.user.service;

import com.fluxbank.corebankingservice.user.dto.UserRequest;
import com.fluxbank.corebankingservice.user.dto.UserResponse;
import com.fluxbank.corebankingservice.user.entity.User;
import com.fluxbank.corebankingservice.user.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setup() {

        user = new User();
        user.setId(1L);
        user.setName("John");
        user.setEmail("john@test.com");
        user.setPhone("9999999999");
    }

    // CREATE USER

    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUser() {

        UserRequest request =
                new UserRequest("John", "john@test.com", "9999999999");

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse response = userService.createUser(request);

        assertNotNull(response);
        assertEquals("John", response.getName());
        assertEquals("john@test.com", response.getEmail());
        assertEquals("9999999999", response.getPhone());

        verify(userRepository).save(any(User.class));
    }

    // GET USER BY ID

    @Test
    @DisplayName("Should return user by id")
    void shouldReturnUserById() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        UserResponse response = userService.getUser(1L);

        assertNotNull(response);
        assertEquals("John", response.getName());

        verify(userRepository).findById(1L);
    }

    // USER NOT FOUND

    @Test
    @DisplayName("Should throw exception when user not found")
    void shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> userService.getUser(1L));

        assertEquals("User not found", exception.getMessage());
    }

    // GET ALL USERS

    @Test
    @DisplayName("Should return all users")
    void shouldReturnAllUsers() {

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jane");
        user2.setEmail("jane@test.com");
        user2.setPhone("8888888888");

        when(userRepository.findAll())
                .thenReturn(List.of(user, user2));

        List<UserResponse> users = userService.getAllUsers();

        assertEquals(2, users.size());

        verify(userRepository).findAll();
    }

    // UPDATE USER

    @Test
    @DisplayName("Should update user")
    void shouldUpdateUser() {

        UserRequest request =
                new UserRequest("John Updated", "john@test.com", "9999999999");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        UserResponse response = userService.updateUser(1L, request);

        assertEquals("John Updated", response.getName());

        verify(userRepository).findById(1L);
        verify(userRepository).save(any(User.class));
    }

    // DELETE USER

    @Test
    @DisplayName("Should delete user successfully")
    void shouldDeleteUser() {

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }
}