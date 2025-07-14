package com.virtualcards.dto.admin;

public record AdminCardResponseDto(
        Long id,
        String name,
        String type,
        int evolutionStage,
        int attack,
        int maxHealth,
        int currentHealth,
        int xp,
        Long userId
) {}