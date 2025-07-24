package com.virtualcards.service.battle;

import com.virtualcards.domain.Card;
import com.virtualcards.domain.enums.Type;
import com.virtualcards.domain.factory.OpponentCardFactory;
import com.virtualcards.dto.battle.OpponentCardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@RequiredArgsConstructor
@Component
public class OpponentCardGenerator {

    private final OpponentCardFactory opponentCardFactory;
    private final Random random = new Random();

    public OpponentCardDto createFairOpponent(Card playerCard) {
        Type randomType = getRandomType();
        int adjustedStage = getBalancedStage(playerCard.getEvolutionStage());

        Card opponentCard = opponentCardFactory.createCard(randomType, adjustedStage);

        return new OpponentCardDto(
                opponentCard.getName(),
                opponentCard.getType(),
                opponentCard.getEvolutionStage(),
                opponentCard.getAttack(),
                opponentCard.getMaxHealth()
        );
    }

    private Type getRandomType() {
        Type[] types = Type.values();
        return types[random.nextInt(types.length)];
    }

    private int getBalancedStage(int playerStage) {
        return switch (playerStage) {
            case 1 -> randomBetween(1, 2);
            case 2 -> randomBetween(2, 3);
            case 3 -> 3;
            default -> 1;
        };
    }

    private int randomBetween(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

}