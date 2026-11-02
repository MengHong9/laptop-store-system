package org.example.laptopstoresystem.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthDto {
    @NotNull(message = "username is required")
    private String username;


    @NotNull(message = "password is required")
    private String password;
}
