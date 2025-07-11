package com.virtualcards.service.battle;

import com.virtualcards.domain.Card;
import com.virtualcards.dto.battle.OpponentCard;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AttackService {

    private final Random random = new Random();
    private final AdvantageService advantageService;

    public AttackService(AdvantageService advantageService) {
        this.advantageService = advantageService;
    }

    public boolean doesPlayerBeginAttacking() {
        return random.nextBoolean();
    }

    public int cardAttack(Card card, OpponentCard opponentCard) {
        int effectiveAttack = advantageService.calculateEffectiveAttack(
                card.getType(),
                opponentCard.getType(),
                card.getAttack(),
                card.getEvolutionStage()
        );
        return applyDamage(opponentCard.getHealth(), effectiveAttack);
    }

    public int opponentCardAttack(Card card, OpponentCard opponentCard) {
        int effectiveAttack = advantageService.calculateEffectiveAttack(
                opponentCard.getType(),
                card.getType(),
                opponentCard.getAttack(),
                opponentCard.getEvolutionStage()
        );
        return applyDamage(card.getCurrentHealth(), effectiveAttack);
    }

    private int applyDamage(int defenderHealth, int attackerEffectiveAttack) {
        return defenderHealth - attackerEffectiveAttack;
    }

}