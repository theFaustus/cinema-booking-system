package com.evil.cbs.web.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, MovieNotFoundException.class, HallNotFoundException.class})
    public ResponseEntity handleNotFoundExceptions(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
