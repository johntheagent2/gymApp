package com.example.gymapp.exception.exception;

import lombok.Getter;

@Getter
public class ExpiredJwtException extends RuntimeException{

    private final String errorCode;

    public ExpiredJwtException(String errorCode){
        this.errorCode = errorCode;
    }
}
