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
        int health;

        switch (type) {
            case ROCK -> {
                name = "Rocky";
                attack = 20;
                health = 50;
            }
            case PAPER -> {
                name = "Papyrus";
                attack = 25;
                health = 45;
            }
            case SCISSORS -> {
                name = "Sheary";
                attack = 30;
                health = 40;
            }
            default -> throw new IllegalArgumentException("Invalid type: " + type);
        }

        return new Card(
                null,
                name,
                type,
                1,
                attack,
                health,
                0,
                userId
        );

    }

}