package com.virtualcards.dto.card;

import com.virtualcards.domain.Card;
import com.virtualcards.domain.enums.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardResponseDto {

    private Long id;
    private String name;
    private Type type;
    private int evolutionStage;
    private int attack;
    private int maxHealth;
    private int currentHealth;
    private int xp;
    private boolean hasAdvantage;
    private int advantageBonus;

    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.name = card.getName();
        this.type = card.getType();
        this.evolutionStage = card.getEvolutionStage();
        this.attack = card.getAttack();
        this.maxHealth = card.getMaxHealth();
        this.currentHealth = card.getCurrentHealth();
        this.xp = card.getXp();
    }

}