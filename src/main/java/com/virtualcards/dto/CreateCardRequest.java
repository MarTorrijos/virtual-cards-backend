package com.virtualcards.dto;

import com.virtualcards.model.enums.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCardRequest {

    private Type type;

}