package org.example.laptopstoresystem.dto.user;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.example.laptopstoresystem.common.enums.UserRole;

import java.time.LocalDateTime;


@Data
@JsonPropertyOrder({"id" , "name" , "password" , "email" , "role" , "created_at" , "updated_at"})
public class UserResponseDto {
    private Long id;
    private String name;
    private String password;
    private String email;
    private UserRole role;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
