package com.virtualcards.controller;

import com.virtualcards.dto.auth.AuthenticationRequestDto;
import com.virtualcards.dto.auth.AuthenticationResponseDto;
import com.virtualcards.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@Valid @RequestBody AuthenticationRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthenticationRequestDto request) {
        authService.registerNewUser(request);
        return ResponseEntity.status(201).build();
    }

}