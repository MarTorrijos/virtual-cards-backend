package com.virtualcards.service.battle;

import com.virtualcards.domain.enums.Type;

public class AdvantageService {

    public int calculateEffectiveAttack(Type attackerType, Type defenderType, int baseAttack, int attackerStage) {
        int bonus = switch (attackerStage) {
            case 1 -> 10;
            case 2 -> 15;
            case 3 -> 20;
            default -> 0;
        };

        return hasTypeAdvantage(attackerType, defenderType) ? baseAttack + bonus : baseAttack;
    }

    private boolean hasTypeAdvantage(Type attacker, Type defender) {
        return (attacker == Type.ROCK && defender == Type.SCISSORS)
                || (attacker == Type.SCISSORS && defender == Type.PAPER)
                || (attacker == Type.PAPER && defender == Type.ROCK);
    }

}