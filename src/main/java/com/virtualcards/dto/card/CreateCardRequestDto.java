package com.virtualcards.dto.card;

import com.virtualcards.domain.enums.Type;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCardRequestDto {

    @NotNull(message = "Card type must be provided")
    private Type type;

}