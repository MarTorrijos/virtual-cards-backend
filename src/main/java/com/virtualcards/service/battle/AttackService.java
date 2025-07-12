package com.virtualcards.service.battle;

import com.virtualcards.domain.Card;
import com.virtualcards.dto.battle.OpponentCardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class AttackService {

    private final Random random = new Random();
    private final AdvantageService advantageService;

    public boolean doesPlayerBeginAttacking() {
        return random.nextBoolean();
    }

    public int cardAttack(Card card, OpponentCardDto opponentCard) {
        int effectiveAttack = advantageService.calculateEffectiveAttack(
                card.getType(),
                opponentCard.getType(),
                card.getAttack(),
                card.getEvolutionStage()
        );
        return applyDamage(opponentCard.getHealth(), effectiveAttack);
    }

    public int opponentCardAttack(Card card, OpponentCardDto opponentCard) {
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