package com.virtualcards.service.card;

import com.virtualcards.domain.Card;
import com.virtualcards.repository.CardRepository;
import org.springframework.stereotype.Service;

@Service
public class CardRecoveryService {

    private final CardRepository cardRepository;

    public CardRecoveryService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card postBattleHealing() {
        // TODO: finish this. null is just a placeholder
        return null;
    }

}