package com.virtualcards.domain.factory;

import com.virtualcards.domain.Card;
import com.virtualcards.domain.enums.Type;

public interface CardFactory {

    Card createCard(Type type, Long userId);

}