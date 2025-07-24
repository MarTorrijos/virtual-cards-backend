package com.virtualcards.service.card;

import com.virtualcards.exception.MaxEvolutionStageReachedException;
import com.virtualcards.exception.NotEnoughXpException;
import com.virtualcards.domain.Card;
import com.virtualcards.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Service
public class CardUpgradeService {

    private final CardRepository cardRepository;
    private final CardCrudService cardCrudService;
    private final CardEvolutionEngine cardEvolutionEngine;

    public Card upgradeAttack(Long cardId) {
        return applyUpgrade(cardId, "attack", card -> card.setAttack(card.getAttack() + 10));
    }

    public Card upgradeHealth(Long cardId) {
        return applyUpgrade(cardId, "health", card -> {
            card.setMaxHealth(card.getMaxHealth() + 10);
            card.setCurrentHealth(card.getMaxHealth());
        });
    }

    private Card applyUpgrade(Long cardId, String context, Consumer<Card> statUpgrade) {
        Card card = cardCrudService.getCard(cardId);
        int xpNeeded = xpRequiredForUpgrade(card);
        validateAndConsumeXp(card, xpNeeded, context);

        statUpgrade.accept(card);
        return cardRepository.save(card);
    }

    private void validateAndConsumeXp(Card card, int xpNeeded, String context) {
        if (card.getXp() < xpNeeded) {
            throw new NotEnoughXpException("Not enough xp to upgrade " + context);
        }
        card.setXp(card.getXp() - xpNeeded);
    }

    private int xpRequiredForUpgrade(Card card) {
        return switch (card.getEvolutionStage()) {
            case 1 -> 75;
            case 2 -> 125;
            case 3 -> 175;
            default -> throw new IllegalArgumentException("Invalid evolution stage");
        };
    }

    public Card evolve(Long cardId) {
        Card card = cardCrudService.getCard(cardId);
        if (card.getEvolutionStage() >= 3) {
            throw new MaxEvolutionStageReachedException();
        }

        int xpNeeded = xpRequiredForEvolution(card);
        validateAndConsumeXp(card, xpNeeded, "evolve");

        cardEvolutionEngine.applyEvolution(card);
        return cardRepository.save(card);
    }

    private int xpRequiredForEvolution(Card card) {
        return switch (card.getEvolutionStage()) {
            case 1 -> 150;
            case 2 -> 300;
            default -> throw new IllegalArgumentException("Invalid or maxed evolution stage");
        };
    }

}