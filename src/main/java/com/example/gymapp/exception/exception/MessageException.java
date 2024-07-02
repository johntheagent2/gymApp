package com.example.gymapp.exception.exception;

import lombok.Getter;

@Getter
public class MessageException extends RuntimeException{

    private final String errorCode;

    public MessageException(String errorCode){
        this.errorCode = errorCode;
    }
}
