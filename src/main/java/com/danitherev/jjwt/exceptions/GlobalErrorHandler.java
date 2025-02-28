package com.danitherev.jjwt.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();

            errores.put(campName, message);
        });

        return new ResponseEntity<>(errores,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDetails> manejarResourceNotFoundException(EntityNotFoundException exception, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApiErrors.class)
    public ResponseEntity<ErrorDetails> customExceptions (ApiErrors exception, WebRequest webRequest){
           ErrorDetails errorDetalles = new ErrorDetails(
            LocalDateTime.now(),             
            exception.getMessage(),
            webRequest.getDescription(false)
        );        
        return new ResponseEntity<>(errorDetalles, exception.getEstado());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> handleBadCredentialsException(BadCredentialsException exception,
                                                                        WebRequest webRequest) {
            ErrorDetails errorDetalles = new ErrorDetails(
            LocalDateTime.now(),
            exception.getMessage(),
            webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetalles, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorDetails> handleNotAuthorizationException(AuthorizationDeniedException ex, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            ex.getMessage(),
            webRequest.getDescription(false)
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN); 
    }



}
