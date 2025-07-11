package com.virtualcards.domain;

import com.virtualcards.domain.enums.Type;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;
    private int evolutionStage;
    private int attack;
    private int maxHealth;
    private int currentHealth;
    private int xp;
    private Long userId;

}