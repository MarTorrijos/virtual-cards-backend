package com.virtualcards.dto.card;

import com.virtualcards.domain.enums.Type;

public record CardResponseDto(
        Long id,
        String name,
        Type type,
        int evolutionStage,
        int attack,
        int maxHealth,
        int currentHealth,
        int xp
) {}