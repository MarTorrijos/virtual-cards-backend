package com.virtualcards.controller;

import com.virtualcards.dto.card.CardResponseDto;
import com.virtualcards.service.card.CardCrudService;
import com.virtualcards.service.card.CardUpgradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
public class CardUpgradeController {

    private final CardUpgradeService cardUpgradeService;
    private final CardCrudService cardCrudService;

    public CardUpgradeController(CardUpgradeService cardUpgradeService, CardCrudService cardCrudService) {
        this.cardUpgradeService = cardUpgradeService;
        this.cardCrudService = cardCrudService;
    }

    @PutMapping("/upgrade/attack/{id}")
    public ResponseEntity<CardResponseDto> upgradeAttack(@PathVariable Long id) {
        var updated = cardUpgradeService.upgradeAttack(id);
        return ResponseEntity.ok(cardCrudService.mapToDto(updated));
    }

    @PutMapping("/upgrade/health/{id}")
    public ResponseEntity<CardResponseDto> upgradeHealth(@PathVariable Long id) {
        var updated = cardUpgradeService.upgradeHealth(id);
        return ResponseEntity.ok(cardCrudService.mapToDto(updated));
    }

    @PutMapping("/evolve/{id}")
    public ResponseEntity<CardResponseDto> evolve(@PathVariable Long id) {
        var evolved = cardUpgradeService.evolve(id);
        return ResponseEntity.ok(cardCrudService.mapToDto(evolved));
    }

}