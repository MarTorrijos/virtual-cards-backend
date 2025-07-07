package com.virtualcards.exception;

public class MaxEvolutionStageReachedException extends RuntimeException {

    public MaxEvolutionStageReachedException() {
        super("Card is already at max evolution stage");
    }

}