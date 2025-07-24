package com.virtualcards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, String>> wrap(String msg, HttpStatus status) {
        return new ResponseEntity<>(Map.of("message", msg), status);
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCardNotFound(CardNotFoundException ex) {
        return wrap(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CooldownNotOverException.class)
    public ResponseEntity<Map<String, String>> handleCooldownNotOver(CooldownNotOverException ex) {
        return wrap(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughXpException.class)
    public ResponseEntity<Map<String, String>> handleNotEnoughXp(NotEnoughXpException ex) {
        return wrap(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxEvolutionStageReachedException.class)
    public ResponseEntity<Map<String, String>> handleMaxEvolutionStageReached(MaxEvolutionStageReachedException ex) {
        return wrap(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorizedAccess(UnauthorizedAccessException ex) {
        return wrap(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException ex) {
        return wrap(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}