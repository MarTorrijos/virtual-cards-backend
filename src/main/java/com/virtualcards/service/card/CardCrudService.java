package com.virtualcards.service.card;

import com.virtualcards.domain.Card;
import com.virtualcards.domain.enums.Type;
import com.virtualcards.domain.factory.CardFactory;
import com.virtualcards.dto.card.CardResponseDto;
import com.virtualcards.exception.CardNotFoundException;
import com.virtualcards.exception.UnauthorizedAccessException;
import com.virtualcards.repository.CardRepository;
import com.virtualcards.service.user.CurrentUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardCrudService {

    private final CardRepository cardRepository;
    private final CardFactory cardFactory;
    private final CurrentUserService currentUserService;

    public CardCrudService(CardRepository cardRepository, CardFactory cardFactory,
                           CurrentUserService currentUserService) {
        this.cardRepository = cardRepository;
        this.cardFactory = cardFactory;
        this.currentUserService = currentUserService;
    }

    public Card createCard(Type type) {
        Long userId = currentUserService.getCurrentUserId();
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

        if (!card.getUserId().equals(currentUserService.getCurrentUserId())) {
            throw new UnauthorizedAccessException("You do not have permission to access this card");
        }

        return card;
    }

    public List<Card> getAllCardsForCurrentUser() {
        Long userId = currentUserService.getCurrentUserId();
        return cardRepository.findByUserId(userId);
    }

    public Card save(Card card) {
        return cardRepository.save(card);
    }

    public CardResponseDto getCardDto(Long id) {
        return mapToDto(getCard(id));
    }

    public List<CardResponseDto> getAllCardsForCurrentUserDto() {
        return getAllCardsForCurrentUser().stream()
                .map(this::mapToDto)
                .toList();
    }

    public CardResponseDto mapToDto(Card card) {
        return new CardResponseDto(
                card.getId(),
                card.getName(),
                card.getType(),
                card.getEvolutionStage(),
                card.getAttack(),
                card.getMaxHealth(),
                card.getCurrentHealth(),
                card.getXp()
        );
    }

}