package com.k4noise.pinspire.adapter.web.errors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

@RestControllerAdvice
public class ValidationAdvice extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        var errors = new ArrayList<>(exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ValidationResponse("Validation error", error.getField(), error.getRejectedValue(), error.getDefaultMessage())).toList());
        return ResponseEntity.badRequest()
                .body(errors);
    }
}