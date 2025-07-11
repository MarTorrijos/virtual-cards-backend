package com.virtualcards.controller;

import com.virtualcards.dto.card.CardResponseDto;
import com.virtualcards.dto.card.CreateCardRequestDto;
import com.virtualcards.service.card.CardCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
public class CardCrudController {

    private final CardCrudService cardCrudService;

    public CardCrudController(CardCrudService cardCrudService) {
        this.cardCrudService = cardCrudService;
    }

    @PostMapping
    public ResponseEntity<CardResponseDto> createCard(@RequestBody CreateCardRequestDto request) {
        CardResponseDto addedCard = cardCrudService.mapToDto(cardCrudService.createCard(request.getType()));
        return ResponseEntity.status(HttpStatus.CREATED).body(addedCard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        cardCrudService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDto> getCard(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(cardCrudService.getCardDto(id));
    }

    @GetMapping
    public ResponseEntity<List<CardResponseDto>> getAllCardsForCurrentUser() {
        return ResponseEntity.ok(cardCrudService.getAllCardsForCurrentUserDto());
    }

}