package com.virtualcards.service.battle;

import com.virtualcards.dto.card.OpponentCard;
import com.virtualcards.domain.Card;
import com.virtualcards.domain.enums.Type;
import com.virtualcards.domain.factory.DefaultCardFactory;
import org.springframework.stereotype.Component;

@Component
public class OpponentCardGenerator {

    private final DefaultCardFactory defaultCardFactory;

    public OpponentCardGenerator(DefaultCardFactory defaultCardFactory) {
        this.defaultCardFactory = defaultCardFactory;
    }

    public OpponentCard createRandomOpponent() {
        Type[] types = Type.values();
        Type type = types[(int) (Math.random() * types.length)];

        Card baseCard = defaultCardFactory.createCard(type, null);

        return new OpponentCard(
                baseCard.getName(),
                baseCard.getType(),
                baseCard.getEvolutionStage(),
                baseCard.getAttack(),
                baseCard.getHealth()
        );
    }

}