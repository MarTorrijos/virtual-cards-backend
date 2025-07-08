package com.virtualcards.service.card;

import com.virtualcards.exception.MaxEvolutionStageReachedException;
import com.virtualcards.exception.NotEnoughEnergyException;
import com.virtualcards.model.Card;
import com.virtualcards.repository.CardRepository;
import com.virtualcards.service.card.logic.CardEvolutionEngine;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class CardUpgradeService {

    private final CardRepository cardRepository;
    private final CardCrudService cardCrudService;
    private final CardEvolutionEngine cardEvolutionEngine;

    public CardUpgradeService(CardRepository cardRepository, CardCrudService cardCrudService,
                              CardEvolutionEngine cardEvolutionEngine) {
        this.cardRepository = cardRepository;
        this.cardCrudService = cardCrudService;
        this.cardEvolutionEngine = cardEvolutionEngine;
    }

    public Card upgradeAttack(Long cardId) {
        return applyUpgrade(cardId, "attack", card -> card.setAttack(card.getAttack() + 10));
    }

    public Card upgradeHealth(Long cardId) {
        return applyUpgrade(cardId, "health", card -> card.setHealth(card.getHealth() + 10));
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
            throw new NotEnoughEnergyException("Not enough xp to " + context);
        }
        card.setXp(card.getXp() - xpNeeded);
    }

    public int xpRequiredForUpgrade(Card card) {
        return switch (card.getEvolutionStage()) {
            case 1 -> 75;
            case 2 -> 125;
            case 3 -> 175;
            default -> throw new IllegalArgumentException("Invalid evolution stage");
        };
    }

    public int xpRequiredForEvolution(Card card) {
        return switch (card.getEvolutionStage()) {
            case 1 -> 150;
            case 2 -> 300;
            default -> throw new IllegalArgumentException("Invalid or maxed evolution stage");
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

}