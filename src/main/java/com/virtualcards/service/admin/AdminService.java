package com.virtualcards.service.admin;

import com.virtualcards.domain.Card;
import com.virtualcards.dto.card.CardResponseDto;
import com.virtualcards.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final CardRepository cardRepository;

    public AdminService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    // TODO

    // getUser()

    // getAllUsers()

    // getCard()

    public List<CardResponseDto> getAllCards() {
        return cardRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private CardResponseDto toDto(Card card) {
        return new CardResponseDto(
                card.getId(),
                card.getName(),
                card.getType(),
                card.getEvolutionStage(),
                card.getAttack(),
                card.getMaxHealth(),
                card.getCurrentHealth(),
                card.getXp()
        );
    }

    // awardXpToCard()

    // deleteCard()

    // deleteUser()

}