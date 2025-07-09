package com.virtualcards.service.battle;

import com.virtualcards.dto.card.OpponentCard;
import com.virtualcards.domain.Card;
import com.virtualcards.domain.enums.Type;
import com.virtualcards.domain.factory.DefaultCardFactory;
import com.virtualcards.service.card.CardEvolutionEngine;
import org.springframework.stereotype.Component;

@Component
public class OpponentCardGenerator {

    private final DefaultCardFactory defaultCardFactory;
    private final CardEvolutionEngine cardEvolutionEngine;

    public OpponentCardGenerator(DefaultCardFactory defaultCardFactory,
                                 CardEvolutionEngine cardEvolutionEngine) {
        this.defaultCardFactory = defaultCardFactory;
        this.cardEvolutionEngine = cardEvolutionEngine;
    }

    public OpponentCard createRandomOpponent() {
        Type[] types = Type.values();
        Type type = types[(int) (Math.random() * types.length)];

        Card baseCard = defaultCardFactory.createCard(type, null);

        // todo: change this and don't let the opponent stage be 2 stages higher than your card
        // if your card is stage 1 the opponent can't be stage 3
        // Also if your card is stage 2 in can't be stage 1, the opponent card will be your card stage or 1 higher
        int stage = (int) (Math.random() * 3) + 1;

        for (int i = 1; i < stage; i++) {
            cardEvolutionEngine.applyEvolution(baseCard);
        }

        return new OpponentCard(
                baseCard.getName(),
                baseCard.getType(),
                baseCard.getEvolutionStage(),
                baseCard.getAttack(),
                baseCard.getHealth()
        );
    }

}