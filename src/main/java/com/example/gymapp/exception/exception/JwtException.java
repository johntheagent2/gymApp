package com.example.gymapp.exception.exception;

import lombok.Getter;

@Getter
public class JwtException extends RuntimeException{

    private final String errorCode;

    public JwtException(String errorCode){
        this.errorCode = errorCode;
    }
}
