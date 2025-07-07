package com.virtualcards.model;

import com.virtualcards.model.enums.Type;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Card {

    @Id
    private final Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private final Type type;
    private int evolutionStage;
    private int health;
    private int attack;
    private int xp;
    private Long userId;

}