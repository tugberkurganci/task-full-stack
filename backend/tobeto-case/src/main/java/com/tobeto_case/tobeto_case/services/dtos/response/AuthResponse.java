package com.tobeto_case.tobeto_case.services.dtos.response;


import com.tobeto_case.tobeto_case.entities.Token;

public record AuthResponse(
        UserResponse user,
        Token token
) {
}

