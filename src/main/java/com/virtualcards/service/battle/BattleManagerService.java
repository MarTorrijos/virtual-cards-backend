package com.virtualcards.service.battle;

import com.virtualcards.domain.Card;
import com.virtualcards.domain.enums.Type;
import com.virtualcards.dto.battle.BattleLogDto;
import com.virtualcards.dto.battle.OpponentCardDto;
import com.virtualcards.dto.card.CardResponseDto;
import com.virtualcards.service.card.CardCrudService;
import com.virtualcards.util.BattleLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BattleManagerService {

    private final AttackService cardCombatService;
    private final OpponentCardGenerator opponentCardGenerator;
    private final CombatResultService combatResultService;
    private final PostBattleHealing postBattleHealing;
    private final CardCrudService cardCrudService;
    private final AdvantageService advantageService;

    public BattleLogDto battle(Card card) {
        BattleLogger logger = new BattleLogger();

        OpponentCardDto opponentCard = opponentCardGenerator.createFairOpponent(card);
        logger.logOpponentGenerated(opponentCard.getName(), opponentCard.getEvolutionStage());

        boolean playerHasAdvantage = advantageService.hasTypeAdvantage(card.getType(), opponentCard.getType());
        boolean opponentHasAdvantage = advantageService.hasTypeAdvantage(opponentCard.getType(), card.getType());

        int playerBonus = playerHasAdvantage ? advantageService.getBonus(card.getEvolutionStage()) : 0;
        int opponentBonus = opponentHasAdvantage ? advantageService.getBonus(opponentCard.getEvolutionStage()) : 0;

        CardResponseDto playerCardDto = new CardResponseDto(card);
        playerCardDto.setHasAdvantage(playerHasAdvantage);
        playerCardDto.setAdvantageBonus(playerBonus);

        opponentCard.setHasAdvantage(opponentHasAdvantage);
        opponentCard.setAdvantageBonus(opponentBonus);

        fightLoop(card, opponentCard, logger);
        resolveOutcome(card, opponentCard, logger);

        cardCrudService.persistBattleResult(card);

        return new BattleLogDto(playerCardDto, opponentCard, logger.getEvents());
    }

    private void fightLoop(Card card, OpponentCardDto opponentCard, BattleLogger logger) {
        boolean playerStarts = cardCombatService.doesPlayerBeginAttacking();
        String starterName = playerStarts ? card.getName() : "Opponent " + opponentCard.getName();
        logger.logStart(starterName);

        boolean isBattleOver = false;

        while (!isBattleOver) {
            isBattleOver = processBattleTurn(card, opponentCard, playerStarts, logger);
            if (!isBattleOver) {
                isBattleOver = processBattleTurn(card, opponentCard, !playerStarts, logger);
            }
        }
    }

    private boolean processBattleTurn(Card card, OpponentCardDto opponentCard, boolean isPlayerTurn, BattleLogger logger) {
        return executeTurn(card, opponentCard, isPlayerTurn, logger);
    }

    private boolean executeTurn(Card card, OpponentCardDto opponentCard, boolean isPlayer, BattleLogger logger) {
        if (isPlayer) {
            int newOpponentHealth = cardCombatService.cardAttack(card, opponentCard);
            newOpponentHealth = Math.max(newOpponentHealth, 0);
            opponentCard.setHealth(newOpponentHealth);
            logger.logAttack(card.getName(), "Opponent " + opponentCard.getName(), newOpponentHealth);
            return newOpponentHealth == 0;
        } else {
            int newPlayerHealth = cardCombatService.opponentCardAttack(card, opponentCard);
            newPlayerHealth = Math.max(newPlayerHealth, 0);
            card.setCurrentHealth(newPlayerHealth);
            logger.logAttack("Opponent " + opponentCard.getName(), card.getName(), newPlayerHealth);
            return newPlayerHealth == 0;
        }
    }

    private void resolveOutcome(Card card, OpponentCardDto opponentCard, BattleLogger logger) {
        if (combatResultService.playerCardWins(opponentCard)) {
            int xpGained = combatResultService.winXP(card);
            card.setXp(card.getXp() + xpGained);
            logger.logWin(card.getName());
            logger.logXpGain(card.getName(), xpGained);
        } else {
            logger.logLoss(card.getName());
        }

        postBattleHealing.startCooldown(card.getId());
    }

}