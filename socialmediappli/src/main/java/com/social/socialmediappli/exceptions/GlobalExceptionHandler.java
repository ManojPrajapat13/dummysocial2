package com.social.socialmediappli.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException userNotFoundException){
        return  new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InfluencerNotFoundException.class)
    public ResponseEntity<String> handleInfluencerNotFoundException(InfluencerNotFoundException influencerNotFoundException){
        return new ResponseEntity<>(influencerNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> handlePlatformNotFoundException(PlatformNotFoundException platformNotFoundException){
        return new ResponseEntity<>(platformNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }


}