package com.virtualcards.controller;

import com.virtualcards.domain.Card;
import com.virtualcards.domain.enums.Type;
import com.virtualcards.service.card.CardUpgradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardUpgradeTests {

    @Mock
    private CardUpgradeService cardUpgradeService;

    @InjectMocks
    private CardUpgradeController cardUpgradeController;
    private Card testCard;

    @BeforeEach
    void setUp() {
        testCard = new Card(1L, "Rocky", Type.ROCK, 1, 20, 50, 0, 1L);
    }

    @Test
    void upgradeAttack() {
        Card upgradedCard = new Card(1L, "Rocky", Type.ROCK, 1, 45, 50, 0, 1L);

        when(cardUpgradeService.upgradeAttack(testCard.getId())).thenReturn(upgradedCard);

        ResponseEntity<Card> response = cardUpgradeController.upgradeAttack(testCard.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(45, response.getBody().getAttack());
    }

    @Test
    void upgradeHealth() {
        Card upgradedCard = new Card(1L, "Rocky", Type.ROCK, 1, 20, 60, 0, 1L);

        when(cardUpgradeService.upgradeHealth(testCard.getId())).thenReturn(upgradedCard);

        ResponseEntity<Card> response = cardUpgradeController.upgradeHealth(testCard.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(60, response.getBody().getHealth());
    }

    @Test
    void evolve() {
        Card evolvedCard = new Card(1L, "Rockon", Type.ROCK, 2, 35, 70, 50, 1L);

        when(cardUpgradeService.evolve(testCard.getId())).thenReturn(evolvedCard);

        Card response = cardUpgradeController.evolve(testCard.getId());

        assertNotNull(response);
        assertEquals(2, response.getEvolutionStage());
        assertEquals("Rockon", response.getName());
    }

    // TODO: negative tests (a card can't be upgraded/evolved...)

}