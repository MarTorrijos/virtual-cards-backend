package com.virtualcards.controller;

import com.virtualcards.dto.card.CardResponseDto;
import com.virtualcards.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/cards")
    public ResponseEntity<List<CardResponseDto>> getAllCards() {
        return ResponseEntity.ok(adminService.getAllCards());
    }

    // todo: finish this. But first finish the service

}