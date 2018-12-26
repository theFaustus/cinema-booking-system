package com.evil.cbs.web.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, MovieNotFoundException.class})
    public ResponseEntity handleNotFoundExceptions(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
