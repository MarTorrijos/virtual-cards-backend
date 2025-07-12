package com.virtualcards.service.user;

import com.virtualcards.dto.user.UpdatePasswordRequestDto;
import com.virtualcards.dto.user.UpdateUsernameRequestDto;
import com.virtualcards.dto.user.UserResponseDto;
import com.virtualcards.domain.User;
import com.virtualcards.repository.CardRepository;
import com.virtualcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final CurrentUserService currentUserService;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto getCurrentUserProfile() {
        User currentUser = currentUserService.getCurrentUser();
        return new UserResponseDto(currentUser.getId(), currentUser.getUsername());
    }

    @Transactional
    public void deleteOwnAccount() {
        User currentUser = currentUserService.getCurrentUser();
        cardRepository.deleteByUserId(currentUser.getId());
        userRepository.deleteById(currentUser.getId());
    }

    public void updateUsername(UpdateUsernameRequestDto dto) {
        User currentUser = currentUserService.getCurrentUser();

        userRepository.findByUsername(dto.getNewUsername()).ifPresent(existingUser -> {
            if (!existingUser.getId().equals(currentUser.getId())) {
                throw new IllegalArgumentException("Username is already taken");
            }
        });

        currentUser.setUsername(dto.getNewUsername());
        userRepository.save(currentUser);
    }

    public void updatePassword(UpdatePasswordRequestDto dto) {
        User currentUser = currentUserService.getCurrentUser();

        String encodedPassword = passwordEncoder.encode(dto.getNewPassword());
        currentUser.setPassword(encodedPassword);

        userRepository.save(currentUser);
    }

}