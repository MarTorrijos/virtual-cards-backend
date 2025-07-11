package com.virtualcards.service.battle;

import com.virtualcards.domain.Card;
import com.virtualcards.dto.battle.BattleLog;
import com.virtualcards.dto.card.OpponentCard;
import com.virtualcards.util.BattleLogger;
import org.springframework.stereotype.Service;

import java.util.Random;

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

        OpponentCard opponentCard = generateFairOpponent(card, logger);
        fightLoop(card, opponentCard, logger);
        resolveOutcome(card, opponentCard, logger);

        return new BattleLog(card, logger.getEvents());
    }

    private OpponentCard generateFairOpponent(Card card, BattleLogger logger) {
        OpponentCard opponentCard = opponentCardGenerator.createRandomOpponent();

        switch (card.getEvolutionStage()) {
            case 1 -> opponentCard.setEvolutionStage(randomBetween(1, 2));
            case 2 -> opponentCard.setEvolutionStage(randomBetween(2, 3));
            case 3 -> opponentCard.setEvolutionStage(3);
        }

        logger.logOpponentGenerated(opponentCard.getName(), opponentCard.getEvolutionStage());
        return opponentCard;
    }

    private void fightLoop(Card card, OpponentCard opponentCard, BattleLogger logger) {
        boolean playerStarts = cardCombatService.doesPlayerBeginAttacking();
        String starterName = playerStarts ? card.getName() : opponentCard.getName();
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
            logger.logAttack(card.getName(), opponentCard.getName(), newOpponentHealth);
            return newOpponentHealth == 0;
        } else {
            int newPlayerHealth = cardCombatService.opponentCardAttack(card, opponentCard);
            newPlayerHealth = Math.max(newPlayerHealth, 0);
            card.setHealth(newPlayerHealth);
            logger.logAttack(opponentCard.getName(), card.getName(), newPlayerHealth);
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

    private int randomBetween(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

}