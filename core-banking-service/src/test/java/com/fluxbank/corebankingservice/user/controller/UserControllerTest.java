package com.fluxbank.corebankingservice.user.controller;

import com.fluxbank.corebankingservice.user.dto.UserRequest;
import com.fluxbank.corebankingservice.user.dto.UserResponse;
import com.fluxbank.corebankingservice.user.service.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    // CREATE USER

    @Test
    @DisplayName("Should create user")
    void shouldCreateUser() throws Exception {

        UserResponse response =
                new UserResponse(1L,"John","john@test.com","9999999999");

        when(userService.createUser(any())).thenReturn(response);

        mockMvc.perform(post("/users")
                        .with(jwt())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name":"John",
                                  "email":"john@test.com",
                                  "phone":"9999999999"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@test.com"))
                .andExpect(jsonPath("$.phone").value("9999999999"));

        verify(userService).createUser(any());
    }

    // GET USER BY ID

    @Test
    @DisplayName("Should return user by id")
    void shouldReturnUserById() throws Exception {

        UserResponse response =
                new UserResponse(1L,"John","john@test.com","9999999999");

        when(userService.getUser(1L)).thenReturn(response);

        mockMvc.perform(get("/users/1")
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@test.com"))
                .andExpect(jsonPath("$.phone").value("9999999999"));

        verify(userService).getUser(1L);
    }

    // GET ALL USERS

    @Test
    @DisplayName("Should return all users")
    void shouldReturnAllUsers() throws Exception {

        List<UserResponse> users = List.of(
                new UserResponse(1L,"John","john@test.com","9999999999"),
                new UserResponse(2L,"Jane","jane@test.com","8888888888")
        );

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users")
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].name").value("Jane"));

        verify(userService).getAllUsers();
    }

    // UPDATE USER

    @Test
    @DisplayName("Should update user")
    void shouldUpdateUser() throws Exception {

        UserResponse response =
                new UserResponse(1L,"John Updated","john@test.com","9999999999");

        when(userService.updateUser(eq(1L), any()))
                .thenReturn(response);

        mockMvc.perform(put("/users/1")
                        .with(jwt())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name":"John Updated",
                                  "email":"john@test.com",
                                  "phone":"9999999999"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"));

        verify(userService).updateUser(eq(1L), any());
    }

    // DELETE USER

    @Test
    @DisplayName("Should delete user")
    void shouldDeleteUser() throws Exception {

        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/users/1")
                        .with(jwt())
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(userService).deleteUser(1L);
    }

    // USER NOT FOUND

    @Test
    @DisplayName("Should return error when user not found")
    void shouldReturnErrorWhenUserNotFound() throws Exception {

        when(userService.getUser(1L))
                .thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(get("/users/1")
                        .with(jwt()))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("User not found"));
    }
}