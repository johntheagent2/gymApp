package com.example.gymapp.exception.exception;

import lombok.Getter;

@Getter
public class BadCredentialException extends RuntimeException{

    private final String errorCode;

    public BadCredentialException(String errorCode){
        this.errorCode = errorCode;
    }
}
