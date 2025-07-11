package com.virtualcards.controller;

import com.virtualcards.dto.auth.AuthenticationRequestDto;
import com.virtualcards.dto.auth.AuthenticationResponseDto;
import com.virtualcards.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto request) {
        AuthenticationResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthenticationRequestDto request) {
        authService.registerNewUser(request);
        return ResponseEntity.ok().build();
    }

}