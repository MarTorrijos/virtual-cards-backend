package com.virtualcards.dto.battle;

import com.virtualcards.domain.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpponentCardDto {

    private String name;
    private Type type;
    private int evolutionStage;
    private int attack;
    private int health;
    private boolean hasAdvantage;
    private int advantageBonus;

}