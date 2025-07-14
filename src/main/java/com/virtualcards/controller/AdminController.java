package com.virtualcards.controller;

import com.virtualcards.dto.admin.AdminCardResponseDto;
import com.virtualcards.dto.admin.XpAwardRequestDto;
import com.virtualcards.dto.user.UserResponseDto;
import com.virtualcards.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getUser(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/card/{id}")
    public ResponseEntity<AdminCardResponseDto> getCard(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getCard(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/cards")
    public ResponseEntity<List<AdminCardResponseDto>> getAllCards() {
        return ResponseEntity.ok(adminService.getAllCards());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/card/{id}/awardxp")
    public ResponseEntity<AdminCardResponseDto> awardXpToCard(
            @PathVariable Long id,
            @RequestBody XpAwardRequestDto dto) {
        return ResponseEntity.ok(adminService.awardXpToCard(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/card/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        adminService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }

}