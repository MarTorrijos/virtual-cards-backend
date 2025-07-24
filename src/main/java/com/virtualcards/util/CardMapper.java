package com.virtualcards.util;

import com.virtualcards.domain.Card;
import com.virtualcards.dto.admin.AdminCardResponseDto;
import com.virtualcards.dto.card.CardResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {

    public CardResponseDto mapToDto(Card card) {
        return new CardResponseDto(card);
    }

    public AdminCardResponseDto mapToAdminDto(Card card) {
        return new AdminCardResponseDto(
                card.getId(),
                card.getName(),
                card.getType().name(),
                card.getEvolutionStage(),
                card.getAttack(),
                card.getMaxHealth(),
                card.getCurrentHealth(),
                card.getXp(),
                card.getUserId()
        );
    }

}