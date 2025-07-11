package com.virtualcards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<String> handleCardNotFound(CardNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughEnergyException.class)
    public ResponseEntity<String> handleNotEnoughEnergy(NotEnoughEnergyException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxEvolutionStageReachedException.class)
    public ResponseEntity<String> handleMaxEvolutionStageReached(MaxEvolutionStageReachedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<String> handleUnauthorizedAccess(UnauthorizedAccessException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CooldownNotOverException.class)
    public ResponseEntity<String> handleCooldownNotOver(CooldownNotOverException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}