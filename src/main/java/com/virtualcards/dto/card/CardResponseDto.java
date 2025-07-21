package com.virtualcards.dto.card;

import com.virtualcards.domain.Card;
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
) {
    public static CardResponseDto from(Card card) {
        return new CardResponseDto(
                card.getId(),
                card.getName(),
                card.getType(),
                card.getEvolutionStage(),
                card.getAttack(),
                card.getMaxHealth(),
                card.getCurrentHealth(),
                card.getXp()
        );
    }
}