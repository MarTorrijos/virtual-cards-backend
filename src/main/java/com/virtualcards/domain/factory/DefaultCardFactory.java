package com.virtualcards.domain.factory;

import com.virtualcards.domain.Card;
import com.virtualcards.domain.enums.Type;
import org.springframework.stereotype.Component;

@Component
public class DefaultCardFactory implements CardFactory {

    @Override
    public Card createCard(Type type, Long userId) {
        String name;
        int attack;
        int maxHealth;

        switch (type) {
            case ROCK -> {
                name = "Rocky";
                attack = 20;
                maxHealth = 50;
            }
            case PAPER -> {
                name = "Papyrus";
                attack = 25;
                maxHealth = 45;
            }
            case SCISSORS -> {
                name = "Sheary";
                attack = 30;
                maxHealth = 40;
            }
            default -> throw new IllegalArgumentException("Invalid type: " + type);
        }

        return new Card(
                null,
                name,
                type,
                1,
                attack,
                maxHealth,
                maxHealth,
                0,
                userId
        );

    }

}