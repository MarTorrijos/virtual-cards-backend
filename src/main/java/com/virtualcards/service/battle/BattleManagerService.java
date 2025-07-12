package com.virtualcards.service.battle;

import com.virtualcards.domain.Card;
import com.virtualcards.dto.battle.BattleLogDto;
import com.virtualcards.dto.battle.OpponentCardDto;
import com.virtualcards.service.card.CardCrudService;
import com.virtualcards.util.BattleLogger;
import org.springframework.stereotype.Service;

@Service
public class BattleManagerService {

    private final AttackService cardCombatService;
    private final OpponentCardGenerator opponentCardGenerator;
    private final CombatResultService combatResultService;
    private final PostBattleHealing postBattleHealing;
    private final CardCrudService cardCrudService;

    public BattleManagerService(AttackService cardCombatService, OpponentCardGenerator opponentCardGenerator,
                                CombatResultService combatResultService, PostBattleHealing postBattleHealing,
                                CardCrudService cardCrudService) {
        this.cardCombatService = cardCombatService;
        this.opponentCardGenerator = opponentCardGenerator;
        this.combatResultService = combatResultService;
        this.postBattleHealing = postBattleHealing;
        this.cardCrudService = cardCrudService;
    }

    public BattleLogDto battle(Card card) {
        BattleLogger logger = new BattleLogger();

        OpponentCardDto opponentCard = opponentCardGenerator.createFairOpponent(card);
        logger.logOpponentGenerated(opponentCard.getName(), opponentCard.getEvolutionStage());

        fightLoop(card, opponentCard, logger);
        resolveOutcome(card, opponentCard, logger);

        cardCrudService.persistBattleResult(card);

        return new BattleLogDto(card, logger.getEvents());
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