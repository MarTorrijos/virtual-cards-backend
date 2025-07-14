package com.virtualcards.controller;

import com.virtualcards.dto.card.CardResponseDto;
import com.virtualcards.dto.card.CreateCardRequestDto;
import com.virtualcards.service.card.CardCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class CardCrudController {

    private final CardCrudService cardCrudService;

    @PostMapping("/card")
    public ResponseEntity<CardResponseDto> createCard(@RequestBody CreateCardRequestDto request) {
        CardResponseDto addedCard = cardCrudService.createCardAndReturnDto(request.getType());
        return ResponseEntity.status(HttpStatus.CREATED).body(addedCard);
    }

    @DeleteMapping("/card/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        cardCrudService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/card/{id}")
    public ResponseEntity<CardResponseDto> getCard(@PathVariable Long id) {
        return ResponseEntity.ok(cardCrudService.getCardForCurrentUserDto(id));
    }

    @GetMapping("/cards")
    public ResponseEntity<List<CardResponseDto>> getAllCardsForCurrentUser() {
        return ResponseEntity.ok(cardCrudService.getAllCardsForCurrentUserDto());
    }

}