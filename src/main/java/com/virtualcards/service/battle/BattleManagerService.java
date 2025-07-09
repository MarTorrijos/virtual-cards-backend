package com.virtualcards.service.battle;

import com.virtualcards.domain.Card;
import com.virtualcards.dto.card.OpponentCard;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class BattleManagerService {

    private final CardCombatService cardCombatService;
    private final OpponentCardGenerator opponentCardGenerator;

    public BattleManagerService(CardCombatService cardCombatService, OpponentCardGenerator opponentCardGenerator) {
        this.cardCombatService = cardCombatService;
        this.opponentCardGenerator = opponentCardGenerator;
    }

    public Card battle(Card card) {
        OpponentCard opponentCard = generateFairOpponent(card);
        fightLoop(card, opponentCard);
        resolveOutcome();

        return card;
    }

    private OpponentCard generateFairOpponent(Card card) {
        OpponentCard opponentCard = opponentCardGenerator.createRandomOpponent();

        switch (card.getEvolutionStage()) {
            case 1 -> opponentCard.setEvolutionStage(randomBetween(1, 2));
            case 2 -> opponentCard.setEvolutionStage(randomBetween(2, 3));
            case 3 -> opponentCard.setEvolutionStage(3);
            default -> System.out.println("Incorrect card evolution stage");
        }

        return opponentCard;
    }

    private void fightLoop(Card card, OpponentCard opponentCard) {
        if (cardCombatService.doesPlayerBeginAttacking()) {
            while (true) {
                int newOpponentHealth = cardCombatService.cardAttack(card, opponentCard);
                opponentCard.setHealth(newOpponentHealth);
                if (newOpponentHealth <= 0) break;

                int newPlayerHealth = cardCombatService.opponentCardAttack(card, opponentCard);
                card.setHealth(newPlayerHealth);
                if (newPlayerHealth <= 0) break;
            }
        } else {
            while (true) {
                int newPlayerHealth = cardCombatService.opponentCardAttack(card, opponentCard);
                card.setHealth(newPlayerHealth);
                if (newPlayerHealth <= 0) break;

                int newOpponentHealth = cardCombatService.cardAttack(card, opponentCard);
                opponentCard.setHealth(newOpponentHealth);
                if (newOpponentHealth <= 0) break;
            }
        }
    }

    private void resolveOutcome() {
        // TODO
            // if player wins
                // award xp
                // it will have a waiting period until the health is fully restored
                // (or not, but fighting with less health is not recommended)
            // if player loses
                // no xp
                // health drops to 0
    }

    private int randomBetween(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

}