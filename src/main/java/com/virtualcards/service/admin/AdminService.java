package com.virtualcards.service.admin;

import com.virtualcards.domain.Card;
import com.virtualcards.domain.User;
import com.virtualcards.dto.admin.AdminCardResponseDto;
import com.virtualcards.dto.user.UserResponseDto;
import com.virtualcards.dto.admin.XpAwardRequestDto;
import com.virtualcards.exception.CardNotFoundException;
import com.virtualcards.exception.UserNotFoundException;
import com.virtualcards.repository.CardRepository;
import com.virtualcards.repository.UserRepository;
import com.virtualcards.util.CardMapper;
import com.virtualcards.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
        return userMapper.mapToDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long userId) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UserNotFoundException("Current user '" + currentUsername + "' not found"));

        User userToDelete = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        if (currentUser.getId().equals(userToDelete.getId())) {
            throw new RuntimeException("Admins cannot delete themselves");
        }

        userRepository.deleteById(userId);
    }

    public AdminCardResponseDto getCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));
        return cardMapper.mapToAdminDto(card);
    }

    public List<AdminCardResponseDto> getAllCards() {
        return cardRepository.findAll().stream()
                .map(cardMapper::mapToAdminDto)
                .collect(Collectors.toList());
    }

    public AdminCardResponseDto awardXpToCard(XpAwardRequestDto dto) {
        if (dto.xp() <= 0) {
            throw new IllegalArgumentException("XP must be greater than zero");
        }

        Card card = cardRepository.findById(dto.cardId())
                .orElseThrow(() -> new CardNotFoundException(dto.cardId()));
        card.setXp(card.getXp() + dto.xp());
        return cardMapper.mapToAdminDto(cardRepository.save(card));
    }

    public void deleteCard(Long cardId) {
        if (!cardRepository.existsById(cardId)) {
            throw new CardNotFoundException(cardId);
        }
        cardRepository.deleteById(cardId);
    }

}