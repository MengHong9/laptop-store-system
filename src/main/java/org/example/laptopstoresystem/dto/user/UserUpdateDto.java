package org.example.laptopstoresystem.dto.user;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.laptopstoresystem.common.enums.UserRole;

@Data
public class UserUpdateDto {
    @NotNull(message = "name is required")
    private String name;

    @NotNull(message = "password is required")
    private String password;

    @NotNull(message = "email is required")
    private String email;


    private UserRole role;
}
