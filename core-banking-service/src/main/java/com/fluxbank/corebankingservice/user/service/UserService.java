package com.fluxbank.corebankingservice.user.service;

import com.fluxbank.corebankingservice.user.dto.*;
import com.fluxbank.corebankingservice.user.entity.User;
import com.fluxbank.corebankingservice.user.mapper.UserMapper;
import com.fluxbank.corebankingservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /// //////// CREATE USER //////////
    public UserResponse createUser(UserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();

        return UserMapper.toResponse(userRepository.save(user));
    }

    /// //////// GET USER //////////
    public UserResponse getUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserMapper.toResponse(user);
    }

    /// //////// GET ALL USERS //////////
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    /// //////// UPDATE USER //////////
    public UserResponse updateUser(Long id, UserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setPhone(request.getPhone());

        return UserMapper.toResponse(userRepository.save(user));
    }

    /// //////// DELETE USER //////////
    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }
}