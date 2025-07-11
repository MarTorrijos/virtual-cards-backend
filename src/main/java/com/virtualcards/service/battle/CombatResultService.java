package com.virtualcards.service.battle;

import com.virtualcards.domain.Card;
import com.virtualcards.dto.card.OpponentCard;

public class CombatResultService {

    public int winXP(Card card) {
        return switch (card.getEvolutionStage()) {
            case 1 -> 30;
            case 2 -> 25;
            case 3 -> 20;
            default -> 0;
        };
    }

    public boolean playerCardWins(Card card, OpponentCard opponentCard) {
        return opponentCard.getHealth() <= 0;
    }

}