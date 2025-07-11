package com.virtualcards.dto.card;

import com.virtualcards.domain.enums.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCardRequestDto {

    private Type type;

}