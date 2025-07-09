package com.virtualcards.controller;

import com.virtualcards.domain.Card;
import com.virtualcards.service.battle.BattleManagerService;
import com.virtualcards.service.card.CardCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/battle")
public class BattleManagerController {

    private final BattleManagerService battleManagerService;
    private final CardCrudService cardCrudService;

    public BattleManagerController(BattleManagerService battleManagerService, CardCrudService cardCrudService) {
        this.battleManagerService = battleManagerService;
        this.cardCrudService = cardCrudService;
    }

    @PostMapping("{id}")
    public ResponseEntity<Card> battle(@PathVariable Long id) {
        Card card = cardCrudService.getCard(id);
        Card updatedCard = battleManagerService.battle(card);
        return ResponseEntity.ok(updatedCard);
    }

}