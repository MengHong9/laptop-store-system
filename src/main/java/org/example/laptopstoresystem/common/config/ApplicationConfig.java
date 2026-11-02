package org.example.laptopstoresystem.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "app-config")
@Configuration
@Data
public class ApplicationConfig {
    private Security security;



    @Data
    public static class Security{
        private String secret;
        private Long expiration;
        private Long refreshTokenExpiration;
    }

}
