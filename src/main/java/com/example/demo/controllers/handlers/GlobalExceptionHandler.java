package com.example.demo.controllers.handlers;

import com.example.demo.controllers.APIError;
import com.example.demo.exceptions.BadDataException;
import com.example.demo.exceptions.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)

    public ResponseEntity<APIError> handleResourceNotFound(NotFoundException ex) {
        APIError apiError = new APIError(
                LocalDateTime.now(),
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadDataException.class)
    public ResponseEntity<APIError> handleBadDataException(BadDataException ex) {
        APIError apiError = new APIError(
                LocalDateTime.now(),
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIError> handleBadDataSentInRequest(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String errorMessage = fieldErrors.stream()
                .map(fieldError ->
                        String.format("Field '%s': %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.joining(" | "));

        APIError apiError = new APIError(
                LocalDateTime.now(),
                errorMessage,
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<APIError> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        APIError apiError = new APIError(
                LocalDateTime.now(),
                "Conflict",
                HttpStatus.CONFLICT.value()
        );
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
}
