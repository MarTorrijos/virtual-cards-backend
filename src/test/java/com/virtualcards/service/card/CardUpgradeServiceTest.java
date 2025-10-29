package com.virtualcards.service.card;

import com.virtualcards.domain.Card;
import com.virtualcards.exception.MaxEvolutionStageReachedException;
import com.virtualcards.exception.NotEnoughXpException;
import com.virtualcards.repository.CardRepository;
import com.virtualcards.testdata.CardData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CardUpgradeServiceTest {

    @Mock
    private CardRepository cardRepository;
    @Mock
    private CardCrudService cardCrudService;
    @Mock
    private CardEvolutionEngine cardEvolutionEngine;

    @InjectMocks
    private CardUpgradeService service;

    private final Card card1 = CardData.stage1Card();
    private final Card cardEvoReady = CardData.stage1CardEvoReady();
    private final Card cardNotEnoughXp = CardData.stage1CardNotEnoughXp();
    private final Card card3 = CardData.stage3Card();

    @Test
    @DisplayName("Upgrades attack when it has enough xp")
    void upgradeAttack_Success() {
        when(cardCrudService.getCard(card1.getId())).thenReturn(card1);
        when(cardRepository.save(any(Card.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Card upgradedCard = service.upgradeAttack(card1.getId());

        assertEquals(30, upgradedCard.getAttack());
        assertEquals(0, upgradedCard.getXp());
    }

    @Test
    @DisplayName("Throws exception when trying to upgrade attack without enough xp")
    void upgradeAttack_NotEnoughXp() {
        when(cardCrudService.getCard(cardNotEnoughXp.getId())).thenReturn(cardNotEnoughXp);

        assertThrows(NotEnoughXpException.class, () -> service.upgradeAttack(cardNotEnoughXp.getId()));
    }

    @Test
    @DisplayName("Upgrades health when it has enough xp")
    void upgradeHealth_Success() {
        when(cardCrudService.getCard(card1.getId())).thenReturn(card1);
        when(cardRepository.save(any(Card.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Card upgradedCard = service.upgradeHealth(card1.getId());

        assertEquals(60, upgradedCard.getMaxHealth());
        assertEquals(0, upgradedCard.getXp());
    }

    @Test
    @DisplayName("Throws exception when trying to upgrade health without enough xp")
    void upgradeHealth_NotEnoughXp() {
        when(cardCrudService.getCard(cardNotEnoughXp.getId())).thenReturn(cardNotEnoughXp);

        assertThrows(NotEnoughXpException.class, () -> service.upgradeHealth(cardNotEnoughXp.getId()));
    }

    @Test
    @DisplayName("Evolves card when it has enough xp")
    void evolve_Success() {
        when(cardCrudService.getCard(cardEvoReady.getId())).thenReturn(cardEvoReady);
        doAnswer(invocation -> {
            Card c = invocation.getArgument(0);
            c.setEvolutionStage(c.getEvolutionStage() + 1);
            return null;
        }).when(cardEvolutionEngine).applyEvolution(any(Card.class));
        when(cardRepository.save(any(Card.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Card upgradedCard = service.evolve(cardEvoReady.getId());

        assertEquals(2, upgradedCard.getEvolutionStage());
        assertEquals(0, upgradedCard.getXp());
    }

    @Test
    @DisplayName("Throws exception when trying to evolve a card without enough xp")
    void evolve_NotEnoughXp() {
        when(cardCrudService.getCard(cardNotEnoughXp.getId())).thenReturn(cardNotEnoughXp);

        assertThrows(NotEnoughXpException.class, () -> service.evolve(cardNotEnoughXp.getId()));
    }

    @Test
    @DisplayName("Throws exception when trying to evolve a card in an invalid stage")
    void evolve_InvalidStage() {
        when(cardCrudService.getCard(card3.getId())).thenReturn(card3);

        assertThrows(MaxEvolutionStageReachedException.class, () -> service.evolve(card3.getId()));
    }

}