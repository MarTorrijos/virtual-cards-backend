package com.virtualcards.model.factory;

import com.virtualcards.model.Card;
import com.virtualcards.model.enums.Type;

public interface CardFactory {

    Card createCard(Type type, Long userId);

}