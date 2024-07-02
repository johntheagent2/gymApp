package com.example.gymapp.exception.exception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException{

    private final String errorCode;

    public UnauthorizedException(String errorCode){
        this.errorCode = errorCode;
    }
}
