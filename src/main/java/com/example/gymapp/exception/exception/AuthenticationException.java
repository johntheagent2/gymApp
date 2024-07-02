package com.example.gymapp.exception.exception;

import lombok.Getter;

@Getter
public class AuthenticationException extends RuntimeException{

    private final String errorCode;

    public AuthenticationException(String errorCode){
        this.errorCode = errorCode;
    }
}
