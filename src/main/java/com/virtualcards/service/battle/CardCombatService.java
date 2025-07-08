package com.virtualcards.service.battle;

import com.virtualcards.model.Card;
import com.virtualcards.repository.CardRepository;
import org.springframework.stereotype.Service;

@Service
public class CardCombatService {

    private final CardRepository cardRepository;

    public CardCombatService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card attack() {
        // TODO: finish this. null is just a placeholder
        return null;
    }

    public Card loseHealth(Card card) {
        // TODO: check if card exists
        return cardRepository.save(card);
    }

    public Card winXP() {
        // TODO: finish this. null is just a placeholder
        return null;
    }

    public void determineWinner() {
        // TODO: finish this
    }

}