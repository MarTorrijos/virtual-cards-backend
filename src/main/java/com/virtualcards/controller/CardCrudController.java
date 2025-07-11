package com.virtualcards.controller;

import com.virtualcards.dto.card.CreateCardRequestDto;
import com.virtualcards.domain.Card;
import com.virtualcards.service.card.CardCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/card")
public class CardCrudController {

    private final CardCrudService cardCrudService;

    public CardCrudController(CardCrudService cardCrudService) {
        this.cardCrudService = cardCrudService;
    }

    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody CreateCardRequestDto request) {
        Card addedCard = cardCrudService.createCard(request.getType());
        return ResponseEntity.status(HttpStatus.CREATED).body(addedCard);
    }

    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable Long id) {
        cardCrudService.deleteCard(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCard(@PathVariable Long id) {
        Card card = cardCrudService.getCard(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(card);
    }

    @GetMapping
    public ResponseEntity<List<Card>> getAllCardsForCurrentUser() {
        List<Card> cards = cardCrudService.getAllCardsForCurrentUser();
        return ResponseEntity.ok(cards);
    }

}