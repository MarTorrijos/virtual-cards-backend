package com.virtualcards;

import com.virtualcards.controller.CardCrudController;
import com.virtualcards.dto.CreateCardRequest;
import com.virtualcards.model.Card;
import com.virtualcards.model.enums.Type;
import com.virtualcards.service.CardCrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardCrudTests {

    @Mock
    private CardCrudService cardCrudService;

    @InjectMocks
    private CardCrudController cardCrudController;
    private Card testCard;

    @BeforeEach
    void setUp() {
        testCard = new Card(1L, "Rocky", Type.ROCK, 1, 20, 50, 0, 1L);
    }

    @Test
    void addCard() {
        CreateCardRequest request = new CreateCardRequest();
        request.setType(Type.ROCK);

        when(cardCrudService.createCard(Type.ROCK)).thenReturn(testCard);

        ResponseEntity<Card> response = cardCrudController.createCard(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testCard, response.getBody());
    }

    @Test
    void deleteCard() {
        Long id = testCard.getId();

        cardCrudController.deleteCard(id);
        verify(cardCrudService).deleteCard(id);
    }

    @Test
    void getCard() {
        Long id = testCard.getId();

        when(cardCrudService.getCard(id)).thenReturn(testCard);

        ResponseEntity<Card> response = cardCrudController.getCard(id);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(testCard, response.getBody());
    }

    @Test
    void getAllCardsForCurrentUser() {
        List<Card> expectedCards = List.of(testCard);

        when(cardCrudService.getAllCardsForCurrentUser()).thenReturn(expectedCards);

        ResponseEntity<List<Card>> response = cardCrudController.getAllCardsForCurrentUser();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCards, response.getBody());
    }

    // TODO: negative tests (a card isn't found, a card can't be created...)

}