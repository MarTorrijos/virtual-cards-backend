package com.virtualcards.service.battle;

import com.virtualcards.domain.enums.Type;
import org.springframework.stereotype.Service;

@Service
public class AdvantageService {

    public int calculateEffectiveAttack(Type attackerType, Type defenderType, int baseAttack, int attackerStage) {
        return hasTypeAdvantage(attackerType, defenderType)
                ? baseAttack + getBonus(attackerStage)
                : baseAttack;
    }

    public boolean hasTypeAdvantage(Type attacker, Type defender) {
        return (attacker == Type.ROCK && defender == Type.SCISSORS)
                || (attacker == Type.SCISSORS && defender == Type.PAPER)
                || (attacker == Type.PAPER && defender == Type.ROCK);
    }

    public int getBonus(int attackerStage) {
        return switch (attackerStage) {
            case 1 -> 10;
            case 2 -> 15;
            case 3 -> 20;
            default -> 0;
        };
    }

}