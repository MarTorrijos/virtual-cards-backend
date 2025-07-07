package com.virtualcards.service;

import com.virtualcards.exception.MaxEvolutionStageReachedException;
import com.virtualcards.exception.NotEnoughEnergyException;
import com.virtualcards.model.Card;
import com.virtualcards.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class CardUpgradeService {

    private final CardRepository cardRepository;
    private final CardCrudService cardCrudService;

    public CardUpgradeService(CardRepository cardRepository, CardCrudService cardCrudService) {
        this.cardRepository = cardRepository;
        this.cardCrudService = cardCrudService;
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
            throw new NotEnoughEnergyException("Not enough energy to upgrade " + context);
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

    public Card evolve(Long cardId) {
        Card existing = cardCrudService.getCard(cardId);

        if (existing.getEvolutionStage() >= 3) {
            throw new MaxEvolutionStageReachedException();
        }

        int xpNeeded = xpRequiredForUpgrade(existing);
        validateAndConsumeXp(existing, xpNeeded, "evolve");

        existing.setEvolutionStage(existing.getEvolutionStage() + 1);
        return cardRepository.save(existing);
    }

}