package com.virtualcards.testdata;

import com.virtualcards.domain.Card;
import com.virtualcards.domain.enums.Type;

import java.util.List;

public class CardData {

    public static Card stage1Card() {
        return new Card(
                1L,            // id
                "Rocky",       // name
                Type.ROCK,     // type
                1,             // evolutionStage
                20,            // attack
                50,            // maxHealth
                50,            // currentHealth
                75,            // xp
                2L             // userId
        );
    }

    public static Card stage1CardEvoReady() {
        return new Card(
                1L,            // id
                "Rocky",       // name
                Type.ROCK,     // type
                1,             // evolutionStage
                20,            // attack
                50,            // maxHealth
                50,            // currentHealth
                150,           // xp
                2L             // userId
        );
    }

    public static Card stage1CardNotEnoughXp() {
        return new Card(
                1L,            // id
                "Rocky",       // name
                Type.ROCK,     // type
                1,             // evolutionStage
                20,            // attack
                50,            // maxHealth
                50,            // currentHealth
                0,             // xp
                2L             // userId
        );
    }

    public static Card stage3Card() {
        return new Card(
                1L,            // id
                "Rockolith",   // name
                Type.ROCK,     // type
                3,             // evolutionStage
                55,            // attack
                105,           // maxHealth
                105,           // currentHealth
                175,           // xp
                2L             // userId
        );
    }

    public static List<Card> cardList() {
        return List.of(
                stage1Card(),
                stage3Card());
    }

}