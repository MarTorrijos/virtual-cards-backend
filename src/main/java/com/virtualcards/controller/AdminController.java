package com.virtualcards.controller;

import com.virtualcards.model.Card;
import com.virtualcards.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/cards")
    public ResponseEntity<List<Card>> getAllCards() {
        return ResponseEntity.ok(adminService.getAllCards());
    }

}