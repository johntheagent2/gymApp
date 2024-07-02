package com.example.gymapp.exception.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException{

    private final String errorCode;

    public BadRequestException(String errorCode){
        this.errorCode = errorCode;
    }
}
