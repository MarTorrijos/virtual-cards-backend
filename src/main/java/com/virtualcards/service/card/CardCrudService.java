package com.virtualcards.service.card;

import com.virtualcards.exception.CardNotFoundException;
import com.virtualcards.exception.UnauthorizedAccessException;
import com.virtualcards.domain.Card;
import com.virtualcards.domain.enums.Type;
import com.virtualcards.domain.factory.CardFactory;
import com.virtualcards.repository.CardRepository;
import com.virtualcards.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardCrudService {

    private final CardRepository cardRepository;
    private final CardFactory cardFactory;
    private final UserRepository userRepository;

    public CardCrudService(CardRepository cardRepository, CardFactory cardFactory, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.cardFactory = cardFactory;
        this.userRepository = userRepository;
    }

    // TODO: possibly more validations

    public Card createCard(Type type) {
        Long userId = getCurrentUserId();
        Card card = cardFactory.createCard(type, userId);
        return cardRepository.save(card);
    }

    public void deleteCard(Long id) {
        Card card = getCard(id);
        cardRepository.delete(card);
    }

    public Card getCard(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id));

        if (!card.getUserId().equals(getCurrentUserId())) {
            throw new UnauthorizedAccessException("You do not have permission to access this card");
        }

        return card;
    }

    public List<Card> getAllCardsForCurrentUser() {
        Long userId = getCurrentUserId();
        return cardRepository.findByUserId(userId);
    }

    private Long getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedAccessException("User not found in security context"))
                .getId();
    }

    public Card save(Card card) {
        return cardRepository.save(card);
    }

}