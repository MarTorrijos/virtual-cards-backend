package com.virtualcards.service.admin;

import com.virtualcards.model.Card;
import com.virtualcards.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final CardRepository cardRepository;

    public AdminService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

}