package com.virtualcards.service.admin;

import com.virtualcards.dto.card.CardResponseDto;
import com.virtualcards.repository.CardRepository;
import com.virtualcards.util.CardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

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