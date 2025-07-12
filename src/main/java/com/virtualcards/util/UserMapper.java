package com.virtualcards.util;

import com.virtualcards.domain.User;
import com.virtualcards.dto.user.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDto mapToDto(User user) {
        return new UserResponseDto(user.getId(), user.getUsername());
    }

}