package com.virtualcards.service.card;

import com.virtualcards.domain.Card;
import com.virtualcards.domain.enums.Type;
import com.virtualcards.domain.factory.CardFactory;
import com.virtualcards.dto.card.CardResponseDto;
import com.virtualcards.exception.CardNotFoundException;
import com.virtualcards.exception.UnauthorizedAccessException;
import com.virtualcards.repository.CardRepository;
import com.virtualcards.service.user.CurrentUserService;
import com.virtualcards.util.CardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CardCrudService {

    private final CardRepository cardRepository;
    private final CardFactory cardFactory;
    private final CurrentUserService currentUserService;
    private final CardMapper cardMapper;

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

    public CardResponseDto getCardForCurrentUserDto(Long id) {
        return cardMapper.mapToDto(getCard(id));
    }

    public List<CardResponseDto> getAllCardsForCurrentUserDto() {
        return getAllCardsForCurrentUser().stream()
                .map(cardMapper::mapToDto)
                .toList();
    }

    public CardResponseDto createCardAndReturnDto(Type type) {
        Card card = createCard(type);
        return cardMapper.mapToDto(card);
    }

    public void persistBattleResult(Card card) {
        if (!card.getUserId().equals(currentUserService.getCurrentUserId())) {
            throw new UnauthorizedAccessException("You do not have permission to update this card");
        }
        cardRepository.save(card);
    }

}