package com.example.movieapp.controller;

import com.example.movieapp.dto.LoginRequest;
import com.example.movieapp.dto.RegisterRequest;
import com.example.movieapp.dto.TokenResponse;
import com.example.movieapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh-token")
    public TokenResponse refreshToken(@RequestHeader("Authorization") String refreshToken) {
        return authService.refreshToken(refreshToken.substring(7)); // Удаляем "Bearer " из токена
    }
}
