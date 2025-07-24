package com.virtualcards.domain.factory;

import com.virtualcards.domain.Card;
import com.virtualcards.domain.enums.Type;
import org.springframework.stereotype.Component;

@Component
public class OpponentCardFactory {

    public Card createCard(Type type, int evolutionStage) {
        String name;
        int attack;
        int maxHealth;

        switch (type) {
            case ROCK -> {
                switch (evolutionStage) {
                    case 1 -> { name = "Rocky"; attack = 20; maxHealth = 50; }
                    case 2 -> { name = "Rockon"; attack = 30; maxHealth = 60; }
                    case 3 -> { name = "Rockolith"; attack = 40; maxHealth = 70; }
                    default -> throw new IllegalArgumentException("Invalid evolution stage");
                }
            }
            case PAPER -> {
                switch (evolutionStage) {
                    case 1 -> { name = "Papyrus"; attack = 25; maxHealth = 45; }
                    case 2 -> { name = "Papyrune"; attack = 35; maxHealth = 55; }
                    case 3 -> { name = "Papyrion"; attack = 45; maxHealth = 65; }
                    default -> throw new IllegalArgumentException("Invalid evolution stage");
                }
            }
            case SCISSORS -> {
                switch (evolutionStage) {
                    case 1 -> { name = "Sheary"; attack = 30; maxHealth = 40; }
                    case 2 -> { name = "Shearix"; attack = 40; maxHealth = 50; }
                    case 3 -> { name = "Shearagon"; attack = 50; maxHealth = 60; }
                    default -> throw new IllegalArgumentException("Invalid evolution stage");
                }
            }
            default -> throw new IllegalArgumentException("Invalid type: " + type);
        }

        return new Card(
                null,
                name,
                type,
                evolutionStage,
                attack,
                maxHealth,
                maxHealth,
                0,
                null // Opponent has no userId
        );
    }

}