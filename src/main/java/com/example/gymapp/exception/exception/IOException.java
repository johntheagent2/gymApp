package com.example.gymapp.exception.exception;

import lombok.Getter;

@Getter
public class IOException extends RuntimeException{

    private final String errorCode;

    public IOException(String errorCode){
        this.errorCode = errorCode;
    }
}
