package com.virtualcards.exception;

public class CooldownNotOverException extends RuntimeException {

    public CooldownNotOverException(String message) {
        super(message);
    }

}
