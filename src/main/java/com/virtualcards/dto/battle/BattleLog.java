package com.virtualcards.dto.battle;

import com.virtualcards.domain.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BattleLog {

    private Card updatedCard;
    private List<String> events;

}