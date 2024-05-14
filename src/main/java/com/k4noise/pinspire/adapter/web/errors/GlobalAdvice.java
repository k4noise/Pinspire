package com.k4noise.pinspire.adapter.web.errors;

import com.k4noise.pinspire.adapter.web.dto.response.ErrorResponseDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.List;

@Log4j2
@RestControllerAdvice
public class GlobalAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        log.error("Entity not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(EntityExistsException.class)
    protected ResponseEntity<ErrorResponseDto> handleEntityExistsException(EntityExistsException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.CONFLICT.value(), ex.getMessage());
        log.error("Entity exists: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponseDto> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        log.error("Access denied: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        List<ValidationResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ValidationResponse("Validation error", error.getField(), error.getRejectedValue(), error.getDefaultMessage()))
                .toList();

        log.error("Validation failed: {}", errors);
        return ResponseEntity.badRequest().body(errors);
    }
}