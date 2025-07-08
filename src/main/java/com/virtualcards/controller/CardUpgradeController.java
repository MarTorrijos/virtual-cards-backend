package com.virtualcards.controller;

import com.virtualcards.model.Card;
import com.virtualcards.service.card.CardUpgradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/card")
public class CardUpgradeController {

    private final CardUpgradeService cardUpgradeService;

    public CardUpgradeController(CardUpgradeService cardUpgradeService) {
        this.cardUpgradeService = cardUpgradeService;
    }

    @PutMapping("/upgrade/attack/{id}")
    public ResponseEntity<Card> upgradeAttack(@PathVariable Long id) {
        Card updated = cardUpgradeService.upgradeAttack(id);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/upgrade/health/{id}")
    public ResponseEntity<Card> upgradeHealth(@PathVariable Long id) {
        Card updated = cardUpgradeService.upgradeHealth(id);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/evolve/{id}")
    public Card evolve(@PathVariable Long id) {
        return cardUpgradeService.evolve(id);
    }

}