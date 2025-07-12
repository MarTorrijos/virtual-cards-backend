package com.virtualcards.service.user;

import com.virtualcards.domain.User;
import com.virtualcards.exception.UnauthorizedAccessException;
import com.virtualcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CurrentUserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedAccessException("User not found in security context"));
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

}