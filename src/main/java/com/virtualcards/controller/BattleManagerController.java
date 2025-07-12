package com.virtualcards.controller;

import com.virtualcards.dto.battle.BattleLogDto;
import com.virtualcards.service.battle.BattleManagerService;
import com.virtualcards.domain.Card;
import com.virtualcards.service.card.CardCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/battle")
public class BattleManagerController {

    private final BattleManagerService battleManagerService;
    private final CardCrudService cardCrudService;

    @PostMapping("/{id}")
    public ResponseEntity<BattleLogDto> battle(@PathVariable Long id) {
        Card playerCard = cardCrudService.getCard(id);
        BattleLogDto battleLog = battleManagerService.battle(playerCard);
        return ResponseEntity.ok(battleLog);
    }

}