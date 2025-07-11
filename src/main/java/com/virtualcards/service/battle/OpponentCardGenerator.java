package com.virtualcards.service.battle;

import com.virtualcards.domain.Card;
import com.virtualcards.domain.enums.Type;
import com.virtualcards.domain.factory.DefaultCardFactory;
import com.virtualcards.dto.card.OpponentCard;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OpponentCardGenerator {

    private final DefaultCardFactory defaultCardFactory;
    private final Random random = new Random();

    public OpponentCardGenerator(DefaultCardFactory defaultCardFactory) {
        this.defaultCardFactory = defaultCardFactory;
    }

    public OpponentCard createFairOpponent(Card playerCard) {
        Type randomType = getRandomType();
        Card baseCard = defaultCardFactory.createCard(randomType, null);

        int adjustedStage = getBalancedStage(playerCard.getEvolutionStage());

        return new OpponentCard(
                baseCard.getName(),
                baseCard.getType(),
                adjustedStage,
                baseCard.getAttack(),
                baseCard.getHealth()
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