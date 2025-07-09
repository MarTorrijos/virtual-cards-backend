package com.virtualcards.service.battle;

import com.virtualcards.domain.Card;
import com.virtualcards.domain.enums.Type;
import com.virtualcards.dto.card.OpponentCard;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CardCombatService {

    private final Random random = new Random();

    public boolean doesPlayerBeginAttacking() {
        return random.nextBoolean();
    }

    public int cardAttack(Card card, OpponentCard opponentCard) {
        int effectiveAttack = calculateEffectiveAttack(card.getType(), opponentCard.getType(),
                card.getAttack());
        return applyDamage(opponentCard.getHealth(), effectiveAttack);
    }

    public int opponentCardAttack(Card card, OpponentCard opponentCard) {
        int effectiveAttack = calculateEffectiveAttack(opponentCard.getType(), card.getType(),
                opponentCard.getAttack());
        return applyDamage(card.getHealth(), effectiveAttack);
    }

    private int applyDamage(int defenderHealth, int attackerEffectiveAttack) {
        return defenderHealth - attackerEffectiveAttack;
    }

    private int calculateEffectiveAttack(Type attackerType, Type defenderType, int baseAttack) {
        return hasTypeAdvantage(attackerType, defenderType) ? baseAttack + 10 : baseAttack;
    }

    private boolean hasTypeAdvantage(Type attacker, Type defender) {
        return (attacker == Type.ROCK && defender == Type.SCISSORS)
                || (attacker == Type.SCISSORS && defender == Type.PAPER)
                || (attacker == Type.PAPER && defender == Type.ROCK);
    }

    public int winXP(Card card) {
        return switch (card.getEvolutionStage()) {
            case 1 -> 30;
            case 2 -> 25;
            case 3 -> 20;
            default -> 0;
        };
    }

    public boolean doesPlayerCardWin(Card card, OpponentCard opponentCard) {
        return opponentCard.getHealth() <= 0;
    }

}