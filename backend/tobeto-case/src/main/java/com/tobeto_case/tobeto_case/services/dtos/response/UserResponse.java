package com.tobeto_case.tobeto_case.services.dtos.response;

import com.tobeto_case.tobeto_case.entities.User;

public record UserResponse(int id, String role, String email) {

    public UserResponse(User user) {
        this(
                user.getId(),
                String.valueOf(user.getRole()),
                user.getEmail()
        );
    }
}