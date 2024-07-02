package com.example.gymapp.exception;

import com.example.gymapp.exception.dto.ApiExceptionResponse;
import com.example.gymapp.exception.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@RestControllerAdvice
public class ApiExceptionHandler {

    @Autowired
    private ResourceBundle resourceBundle;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ApiRequestException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<List<ApiExceptionResponse>> handleInvalidArgument(MethodArgumentNotValidException exception){
        ArrayList<ApiExceptionResponse> apiExceptionResponses = new ArrayList<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> apiExceptionResponses.add(new ApiExceptionResponse(
                        error.getField(),
                        resourceBundle.getString(error.getDefaultMessage()),
                        error.getObjectName() + ".error." + error.getCode()
                )));

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(apiExceptionResponses);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ApiExceptionResponse> handleNotFoundException(NotFoundException exception){
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(getErrorMessage(exception.getErrorCode()), exception.getErrorCode());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(apiExceptionResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<ApiExceptionResponse> handleBadRequestException(BadRequestException exception){
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(getErrorMessage(exception.getErrorCode()), exception.getErrorCode());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(apiExceptionResponse);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {IOException.class})
    public ResponseEntity<ApiExceptionResponse> handleIOException(IOException exception) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(getErrorMessage(exception.getErrorCode()), exception.getErrorCode());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiExceptionResponse);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {TemplateException.class})
    public ResponseEntity<ApiExceptionResponse> handleTemplateException(TemplateException exception) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(getErrorMessage(exception.getErrorCode()), exception.getErrorCode());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiExceptionResponse);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<ApiExceptionResponse> handleUnauthorizedException(UnauthorizedException exception) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(getErrorMessage(exception.getErrorCode()), exception.getErrorCode());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(apiExceptionResponse);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {BadCredentialException.class})
    public ResponseEntity<ApiExceptionResponse> handleBadCredential(BadCredentialException exception) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(getErrorMessage(exception.getErrorCode()), exception.getErrorCode());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(apiExceptionResponse);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = {OptimisticLockException.class})
    public ResponseEntity<ApiExceptionResponse> handleOptimisticLockException(OptimisticLockException exception) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(getErrorMessage(exception.getErrorCode()), exception.getErrorCode());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(apiExceptionResponse);
    }

    private String getErrorMessage(String code){
        return resourceBundle.getString(code);
    }
}
