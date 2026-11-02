package org.example.laptopstoresystem.dto.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class ChangePasswordDto {


    @NotBlank(message = "old password is required")
    @Size(min = 6 , max = 15 , message = "password must be between 6 to 15 characters")
    private String oldPassword;


    @NotBlank(message = "new password is required")
    @Size(min = 6 , max = 15 , message = "password must be between 6 to 15 characters")
    private String newPassword;


    @NotBlank(message = "confirm password is required")
    @Size(min = 6 , max = 15 , message = "password must be between 6 to 15 characters")
    private String confirmPassword;
}
