package com.virtualcards.service.battle;

import com.virtualcards.domain.Card;
import com.virtualcards.exception.CardNotFoundException;
import com.virtualcards.exception.CooldownNotOverException;
import com.virtualcards.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class PostBattleHealing {

    private final CardRepository cardRepository;

    private final Map<Long, LocalDateTime> cooldownMap = new ConcurrentHashMap<>();

    private static final int COOLDOWN_SECONDS = 6;

    public void startCooldown(Long cardId) {
        cooldownMap.put(cardId, LocalDateTime.now().plusSeconds(COOLDOWN_SECONDS));
    }

    public boolean isCardReady(Long cardId) {
        LocalDateTime endTime = cooldownMap.get(cardId);
        return endTime == null || LocalDateTime.now().isAfter(endTime);
    }

    public Card healCard(Long cardId) {
        if (!isCardReady(cardId)) {
            throw new CooldownNotOverException("Card is still in cooldown.");
        }

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));

        card.setCurrentHealth(card.getMaxHealth());
        cooldownMap.remove(cardId);
        return cardRepository.save(card);
    }

    @Scheduled(fixedRate = 1000)
    public void healCardsIfReady() {
        cooldownMap.forEach((cardId, endTime) -> {
            if (LocalDateTime.now().isAfter(endTime)) {
                try {
                    healCard(cardId);
                    System.out.println("Auto-healed card " + cardId);
                } catch (Exception ignored) {
                }
            }
        });
    }

}