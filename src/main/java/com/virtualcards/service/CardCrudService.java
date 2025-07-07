package com.virtualcards.service;

import com.virtualcards.exception.CardNotFoundException;
import com.virtualcards.model.Card;
import com.virtualcards.model.enums.Type;
import com.virtualcards.model.factory.CardFactory;
import com.virtualcards.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardCrudService {

    private final CardRepository cardRepository;
    private final CardFactory cardFactory;

    public CardCrudService(CardRepository cardRepository, CardFactory cardFactory) {
        this.cardRepository = cardRepository;
        this.cardFactory = cardFactory;
    }

    // TODO: possibly more validations

    public Card createCard(Type type, Long userId) {
        Card card = cardFactory.createCard(type, userId);
        return cardRepository.save(card);
    }

    public void deleteCard(Long id) {
        Card card = getCard(id);
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