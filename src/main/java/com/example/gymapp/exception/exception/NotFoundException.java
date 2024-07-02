package com.example.gymapp.exception.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String errorCode;

    public NotFoundException(String errorCode){
        this.errorCode = errorCode;
    }
}
