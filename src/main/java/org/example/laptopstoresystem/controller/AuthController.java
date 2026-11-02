package org.example.laptopstoresystem.controller;

import jakarta.validation.Valid;
import org.example.laptopstoresystem.common.response.Response;
import org.example.laptopstoresystem.dto.auth.AuthDto;
import org.example.laptopstoresystem.dto.auth.AuthResponseDto;
import org.example.laptopstoresystem.dto.auth.RefreshTokenDto;
import org.example.laptopstoresystem.dto.auth.RefreshTokenResponseDto;
import org.example.laptopstoresystem.dto.user.UserDto;
import org.example.laptopstoresystem.service.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@Valid @RequestBody UserDto payload){
        AuthResponseDto dto = authService.register(payload);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success("201" , "success" , "successfully registered user" , dto));
    }


    @PostMapping("/login")
    public ResponseEntity<Response> login(@Valid @RequestBody AuthDto payload){
        AuthResponseDto dto = authService.login(payload);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success("200" , "success" , "successfully logged in user" , dto));

    }


    @PostMapping("/refresh")
    public ResponseEntity<Response> refreshToken(@Valid @RequestBody RefreshTokenDto payload){
        RefreshTokenResponseDto dto = authService.refreshToken(payload);

        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success("201" , "success" , "successfully created refresh token" , dto));
    }
}
