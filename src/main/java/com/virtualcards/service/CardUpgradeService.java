package com.virtualcards.service;

import com.virtualcards.model.Card;
import com.virtualcards.repository.CardRepository;

public class CardUpgradeService {

    private final CardRepository cardRepository;

    public CardUpgradeService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card upgradeAttack() {
        // TODO: finish this. null is just a placeholder
        return null;
    }

    public Card upgradeHealth() {
        // TODO: finish this. null is just a placeholder
        return null;
    }

    public Card evolve() {
        // TODO: finish this. null is just a placeholder
        return null;
    }

}