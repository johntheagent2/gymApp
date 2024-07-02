package com.example.gymapp.exception.exception;

import lombok.Getter;

@Getter
public class TemplateException extends RuntimeException{

    private final String errorCode;

    public TemplateException(String errorCode){
        this.errorCode = errorCode;
    }
}
