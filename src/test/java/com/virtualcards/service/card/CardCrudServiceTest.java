package com.virtualcards.service.card;

import com.virtualcards.domain.Card;
import com.virtualcards.domain.enums.Type;
import com.virtualcards.domain.factory.CardFactory;
import com.virtualcards.dto.card.CardResponseDto;
import com.virtualcards.exception.CardNotFoundException;
import com.virtualcards.exception.UnauthorizedAccessException;
import com.virtualcards.repository.CardRepository;
import com.virtualcards.service.user.CurrentUserService;
import com.virtualcards.testdata.CardData;
import com.virtualcards.util.CardMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardCrudServiceTest {

    @Mock
    private CardRepository cardRepository;
    @Mock
    private CardFactory cardFactory;
    @Mock
    private CurrentUserService currentUserService;
    @Mock
    private CardMapper cardMapper;

    @InjectMocks
    private CardCrudService service;

    private final Card card = CardData.stage1Card();
    private final List<Card> cardList = CardData.cardList();
    private final Type cardType = Type.ROCK;

    @Test
    @DisplayName("Create a card successfully")
    void createCard_Success() {
        when(currentUserService.getCurrentUserId()).thenReturn(card.getUserId());
        when(cardFactory.createCard(cardType, card.getUserId())).thenReturn(card);
        when(cardRepository.save(card)).thenReturn(card);

        Card result = service.createCard(cardType);

        assertNotNull(result);
        assertEquals(card, result);
        verify(cardRepository, times(1)).save(card);
        verify(cardFactory, times(1)).createCard(cardType, card.getUserId());
    }

    // TODO: More tests de crear carta?

    @Test
    @DisplayName("Delete a card successfully")
    void deleteCard_Success() {
        when(currentUserService.getCurrentUserId()).thenReturn(card.getUserId());
        when(cardRepository.findById(card.getId())).thenReturn(java.util.Optional.of(card));

        service.deleteCard(card.getId());

        verify(cardRepository, times(1)).delete(card);
    }

    @Test
    @DisplayName("Can't delete a card when card it's not found")
    void deleteCard_CardNotFound() {
        when(cardRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class, () -> service.deleteCard(3L));
        verify(cardRepository, never()).delete(any(Card.class));
    }

    @Test
    @DisplayName("Can't delete a card when user is not current user")
    void deleteCard_NotCurrentUser() {
        when(currentUserService.getCurrentUserId()).thenReturn(3L);
        when(cardRepository.findById(card.getId())).thenReturn(java.util.Optional.of(card));

        assertThrows(UnauthorizedAccessException.class, () -> service.deleteCard(card.getId()));
    }

    @Test
    @DisplayName("Get all cards for current user successfully")
    void getAllCardsForCurrentUserDto_Success() {
        Long currentUserId = card.getUserId();
        when(currentUserService.getCurrentUserId()).thenReturn(currentUserId);
        when(cardRepository.findByUserId(currentUserId)).thenReturn(cardList);

        List<CardResponseDto> expectedDtos = cardList.stream()
                .map(cardMapper::mapToDto)
                .toList();

        List<CardResponseDto> listResult = service.getAllCardsForCurrentUserDto();

        assertNotNull(listResult);
        assertEquals(expectedDtos, listResult);
    }

    @Test
    @DisplayName("")
    void getAllCardsForCurrentUser_NotCurrentUser() {
        // TODO

    }

    @Test
    @DisplayName("")
    void persistBattleResult_Success() {
        // TODO

    }

    @Test
    @DisplayName("")
    void persistBattleResult_NotCurrentUser() {
        // TODO

    }

}