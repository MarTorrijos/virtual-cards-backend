package com.virtualcards.dto.battle;

import com.virtualcards.domain.enums.Type;

public record OpponentCardDto(
        String name,
        Type type,
        int evolutionStage,
        int attack,
        int health
) {}