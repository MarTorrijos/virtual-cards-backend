package com.virtualcards.dto.card;

import com.virtualcards.domain.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardResponseDto {

    private Long id;
    private String name;
    private Type type;
    private int evolutionStage;
    private int attack;
    private int maxHealth;
    private int currentHealth;
    private int xp;
    // if in the future I need to include userId (for admins) a separate specific admin DTO can be made

}