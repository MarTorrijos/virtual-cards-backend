package com.virtualcards.dto.battle;

import com.virtualcards.dto.card.CardResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BattleLogDto {

    private CardResponseDto updatedCard;
    private OpponentCardDto opponentCard;
    private List<String> events;

}