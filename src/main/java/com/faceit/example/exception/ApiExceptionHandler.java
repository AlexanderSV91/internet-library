package com.faceit.example.exception;

import com.faceit.example.util.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handlerApiRequestException(ApiRequestException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        Map<String, String> message = Utils.buildMap(e.getMessage(),
                Utils.getMessageForLocale(e.getMessage()));

        ApiException apiException = Utils.buildApiException(
                e.getClass().getSimpleName(),
                message,
                httpStatus,
                LocalDateTime.now());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {ResourceAlreadyExists.class})
    public ResponseEntity<Object> handlerResourceAlreadyExistException(ResourceAlreadyExists e) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        Map<String, String> message = Utils.buildMap(e.getMessage(),
                Utils.getMessageForLocale(e.getNameResource()));

        ApiException apiException = Utils.buildApiException(
                e.getClass().getSimpleName(),
                message,
                httpStatus,
                LocalDateTime.now());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handlerResourceNotFoundException(ResourceNotFoundException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        Map<String, String> message = Utils.buildMap(e.getMessage(),
                Utils.getMessageForLocale(e.getMessage()));

        ApiException apiException = Utils.buildApiException(
                e.getClass().getSimpleName(),
                message,
                httpStatus,
                LocalDateTime.now());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<Object> handlerHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;

        Map<String, String> message = Utils.buildMap(e.getMessage(), e.getLocalizedMessage());

        ApiException apiException = Utils.buildApiException(
                e.getClass().getSimpleName(),
                message,
                httpStatus,
                LocalDateTime.now());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<Object> handlerSQLIntegrityConstraintViolationException(
            SQLIntegrityConstraintViolationException e) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;

        Map<String, String> message = Utils.buildMap(e.getMessage(), e.getSQLState());

        ApiException apiException = Utils.buildApiException(
                e.getClass().getSimpleName(),
                message,
                httpStatus,
                LocalDateTime.now());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleViolationExceptions(ConstraintViolationException e) {
        HttpStatus httpStatus = HttpStatus.LENGTH_REQUIRED;

        Map<String, String> message = new HashMap<>();
        e.getConstraintViolations().forEach(constraintViolation -> {
            String fieldName = constraintViolation.getPropertyPath().toString();

            String errorMessage = Utils.getMessageForLocale(constraintViolation.getMessage());
            message.put(fieldName, errorMessage);
        });

        ApiException apiException = Utils.buildApiException(
                e.getClass().getSimpleName(),
                message,
                httpStatus,
                LocalDateTime.now());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException e) {
        HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;

        Map<String, String> message = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = Utils.getMessageForLocale(error.getDefaultMessage());
            message.put(fieldName, errorMessage);
        });

        ApiException apiException = Utils.buildApiException(
                e.getClass().getSimpleName(),
                message,
                httpStatus,
                LocalDateTime.now());
        return new ResponseEntity<>(apiException, httpStatus);
    }
}
