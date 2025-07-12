package com.virtualcards.service.admin;

import com.virtualcards.domain.Card;
import com.virtualcards.dto.card.CardResponseDto;
import com.virtualcards.repository.CardRepository;
import com.virtualcards.util.CardMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public AdminService(CardRepository cardRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
    }

// TODO

    // getUser()

    // getAllUsers()

    // getCard()

    public List<CardResponseDto> getAllCards() {
        return cardRepository.findAll().stream()
                .map(cardMapper::mapToDto)
                .collect(Collectors.toList());
    }


    // awardXpToCard()

    // deleteCard()

    // deleteUser()

}