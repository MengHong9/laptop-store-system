package org.example.laptopstoresystem.dto.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.laptopstoresystem.common.enums.UserRole;


@Data
public class UserDto {


    @NotBlank(message = "username is required")
    @Size(min = 5 ,  max = 20 , message = "name must be between 5 to 20 characters")
    private String name;

    @NotBlank(message = "password is required")
    @Size(min = 6 ,  max = 15 , message = "password must be between 6 to 15 characters")
    private String password;

    @NotBlank(message = "email is required")
    @Email(message = "invalid email format")
    @Size(min = 15 ,  max = 30 , message = "password must be between 15 to 30 characters")
    private String email;


    private UserRole role =  UserRole.USER;
}
