package org.example.laptopstoresystem.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefreshTokenDto {
    @JsonProperty("refresh_token")
    @NotBlank(message = "refresh token can not be empty")
    @NotNull(message = "refresh token is required")
    String refreshToken;

}
