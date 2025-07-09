package com.virtualcards.service.card;

import com.virtualcards.domain.Card;
import com.virtualcards.domain.enums.Type;
import org.springframework.stereotype.Component;

@Component
public class CardEvolutionEngine {

    public void applyEvolution(Card card) {
        int currentStage = card.getEvolutionStage();
        Type type = card.getType();

        switch (type) {
            case ROCK -> evolveRock(card, currentStage);
            case PAPER -> evolvePaper(card, currentStage);
            case SCISSORS -> evolveScissors(card, currentStage);
            default -> throw new IllegalArgumentException("Unknown card type: " + type);
        }

        card.setEvolutionStage(currentStage + 1);
    }

    private void evolveRock(Card card, int stage) {
        switch (stage) {
            case 1 -> {
                card.setName("Rockon");
                card.setAttack(card.getAttack() + 15);
                card.setHealth(card.getHealth() + 25);
            }
            case 2 -> {
                card.setName("Rockolith");
                card.setAttack(card.getAttack() + 20);
                card.setHealth(card.getHealth() + 30);
            }
            default -> throw new IllegalArgumentException("Invalid evolution stage for rock type: " + stage);
        }
    }

    private void evolvePaper(Card card, int stage) {
        switch (stage) {
            case 1 -> {
                card.setName("Papyrune");
                card.setAttack(card.getAttack() + 20);
                card.setHealth(card.getHealth() + 20);
            }
            case 2 -> {
                card.setName("Papyrion");
                card.setAttack(card.getAttack() + 25);
                card.setHealth(card.getHealth() + 25);
            }
            default -> throw new IllegalArgumentException("Invalid evolution stage for paper type: " + stage);
        }
    }

    private void evolveScissors(Card card, int stage) {
        switch (stage) {
            case 1 -> {
                card.setName("Shearix");
                card.setAttack(card.getAttack() + 25);
                card.setHealth(card.getHealth() + 15);
            }
            case 2 -> {
                card.setName("Shearagon");
                card.setAttack(card.getAttack() + 30);
                card.setHealth(card.getHealth() + 20);
            }
            default -> throw new IllegalArgumentException("Invalid evolution stage for scissors type: " + stage);
        }
    }

}