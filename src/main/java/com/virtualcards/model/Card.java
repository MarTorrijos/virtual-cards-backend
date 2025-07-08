package com.virtualcards.model;

import com.virtualcards.model.enums.Type;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;
    private int evolutionStage;
    private int attack;
    private int health;
    private int xp;
    private Long userId;

}