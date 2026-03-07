package com.fluxbank.corebankingservice.user.mapper;

import com.fluxbank.corebankingservice.user.dto.UserResponse;
import com.fluxbank.corebankingservice.user.entity.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }
}