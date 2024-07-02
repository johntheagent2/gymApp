package com.example.gymapp.exception.exception;

import lombok.Getter;

@Getter
public class OptimisticLockException extends RuntimeException {

    private final String errorCode;

    public OptimisticLockException(String errorCode) {
        this.errorCode = errorCode;
    }
}
