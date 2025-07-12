package com.virtualcards.controller;

import com.virtualcards.dto.user.UpdatePasswordRequestDto;
import com.virtualcards.dto.user.UpdateUsernameRequestDto;
import com.virtualcards.dto.user.UserResponseDto;
import com.virtualcards.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUserProfile() {
        return ResponseEntity.ok(userService.getCurrentUserProfile());
    }

    @PutMapping("/me/username")
    public ResponseEntity<Void> updateUsername(@Valid @RequestBody UpdateUsernameRequestDto dto) {
        userService.updateUsername(dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UpdatePasswordRequestDto dto) {
        userService.updatePassword(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteOwnAccount() {
        userService.deleteOwnAccount();
        return ResponseEntity.noContent().build();
    }

}