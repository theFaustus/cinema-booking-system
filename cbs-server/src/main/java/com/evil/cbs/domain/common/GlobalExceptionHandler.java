package com.evil.cbs.domain.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.OptimisticLockException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, MovieNotFoundException.class, HallNotFoundException.class, MovieSessionNotFoundException.class})
    public ResponseEntity handleNotFoundExceptions(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler({SeatAlreadyBookedException.class, OptimisticLockException.class})
    public ResponseEntity handleSeatAlreadyBookedException(RuntimeException e) {return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getMessage()); }

    @ExceptionHandler({UserNotAuthenticatedException.class})
    public ResponseEntity handleUserNotAuthenticatedException(RuntimeException e) {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); }

}
