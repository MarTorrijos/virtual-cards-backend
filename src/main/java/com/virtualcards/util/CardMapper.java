package com.virtualcards.util;

import com.virtualcards.domain.Card;
import com.virtualcards.dto.card.CardResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {

    public CardResponseDto mapToDto(Card card) {
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