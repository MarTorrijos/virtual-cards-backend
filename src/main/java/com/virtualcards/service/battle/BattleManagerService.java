package com.virtualcards.service.battle;

import com.virtualcards.domain.Card;
import com.virtualcards.dto.battle.BattleLog;
import com.virtualcards.dto.card.OpponentCard;
import com.virtualcards.util.BattleLogger;
import org.springframework.stereotype.Service;

@Service
public class BattleManagerService {

    private final AttackService cardCombatService;
    private final OpponentCardGenerator opponentCardGenerator;
    private final CombatResultService combatResultService;

    public BattleManagerService(AttackService cardCombatService, OpponentCardGenerator opponentCardGenerator,
                                CombatResultService combatResultService) {
        this.cardCombatService = cardCombatService;
        this.opponentCardGenerator = opponentCardGenerator;
        this.combatResultService = combatResultService;
    }

    public BattleLog battle(Card card) {
        BattleLogger logger = new BattleLogger();

        OpponentCard opponentCard = opponentCardGenerator.createFairOpponent(card);
        logger.logOpponentGenerated(opponentCard.getName(), opponentCard.getEvolutionStage());

        fightLoop(card, opponentCard, logger);
        resolveOutcome(card, opponentCard, logger);

        return new BattleLog(card, logger.getEvents());
    }

    private void fightLoop(Card card, OpponentCard opponentCard, BattleLogger logger) {
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

    private boolean processBattleTurn(Card card, OpponentCard opponentCard, boolean isPlayerTurn, BattleLogger logger) {
        return executeTurn(card, opponentCard, isPlayerTurn, logger);
    }

    private boolean executeTurn(Card card, OpponentCard opponentCard, boolean isPlayer, BattleLogger logger) {
        if (isPlayer) {
            int newOpponentHealth = cardCombatService.cardAttack(card, opponentCard);
            newOpponentHealth = Math.max(newOpponentHealth, 0);
            opponentCard.setHealth(newOpponentHealth);
            logger.logAttack(card.getName(), " Opponent " + opponentCard.getName(), newOpponentHealth);
            return newOpponentHealth == 0;
        } else {
            int newPlayerHealth = cardCombatService.opponentCardAttack(card, opponentCard);
            newPlayerHealth = Math.max(newPlayerHealth, 0);
            card.setHealth(newPlayerHealth);
            logger.logAttack("Opponent " + opponentCard.getName(), card.getName(), newPlayerHealth);
            return newPlayerHealth == 0;
        }
    }

    private void resolveOutcome(Card card, OpponentCard opponentCard, BattleLogger logger) {
        if (combatResultService.playerCardWins(opponentCard)) {
            int xpGained = combatResultService.winXP(card);
            card.setXp(card.getXp() + xpGained);
            logger.logWin(card.getName());
            logger.logXpGain(card.getName(), xpGained);
        } else {
            logger.logLoss(card.getName());
        }
    }

}