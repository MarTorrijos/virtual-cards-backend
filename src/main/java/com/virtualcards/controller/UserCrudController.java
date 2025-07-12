package com.virtualcards.controller;

import com.virtualcards.dto.user.UpdatePasswordRequestDto;
import com.virtualcards.dto.user.UpdateUsernameRequestDto;
import com.virtualcards.dto.user.UserResponseDto;
import com.virtualcards.service.user.UserCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserCrudController {

    private final UserCrudService userCrudService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUserProfile() {
        return ResponseEntity.ok(userCrudService.getCurrentUserProfile());
    }

    @PutMapping("/me/username")
    public ResponseEntity<Void> updateUsername(@Valid @RequestBody UpdateUsernameRequestDto dto) {
        userCrudService.updateUsername(dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UpdatePasswordRequestDto dto) {
        userCrudService.updatePassword(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteOwnAccount() {
        userCrudService.deleteOwnAccount();
        return ResponseEntity.noContent().build();
    }

}