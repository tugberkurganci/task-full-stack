package com.tobeto_case.tobeto_case.services.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record Credentials(
        @Email(message = "{email.invalidformat}") String email,
        @NotBlank(message = "{password.notblank}") String password
) {
}