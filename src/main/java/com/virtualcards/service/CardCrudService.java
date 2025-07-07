package com.virtualcards.service;

import com.virtualcards.exception.CardNotFoundException;
import com.virtualcards.model.Card;
import com.virtualcards.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardCrudService {

    private final CardRepository cardRepository;

    public CardCrudService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    // TODO: possibly more validations

    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    public void deleteCard(Long id) {
        // TODO: check if cards exists, then delete it
        cardRepository.deleteById(id);
    }

    public Card getCard(Long id) {
        return cardRepository.findById(id).
                orElseThrow(() -> new CardNotFoundException(id));
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

}