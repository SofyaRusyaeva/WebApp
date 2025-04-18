package com.example.WebApp.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.rmi.AccessException;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleRouteNotFoundException(ObjectNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "status", HttpStatus.NOT_FOUND.value(),
                        "message", e.getMessage()
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "message", e.getBindingResult().getAllErrors().get(0).getDefaultMessage()
                )
        );
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateBrandException(DuplicateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "message", e.getMessage()
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Map.of(
                        "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "message", e.getMessage(),
                        "class", e.getClass()
                )
        );
    }

    @ExceptionHandler({NoHandlerFoundException.class, MethodArgumentTypeMismatchException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Map<String, Object>> handleNoHandlerFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "status", HttpStatus.NOT_FOUND.value(),
                        "message", "The requested URL was not found"
                )
        );
    }

    @ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class, BadCredentialsException.class})
    public ResponseEntity<Map<String, Object>> handleEnumConversionError(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "message", "Invalid value provided"
                )
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCredentials(InvalidCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of(
                        "status", HttpStatus.UNAUTHORIZED.value(),
                        "message", e.getMessage()
                )
        );
    }

    @ExceptionHandler(AccessException.class)
    public ResponseEntity<Map<String, Object>> handleAccessException(AccessException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                Map.of(
                        "status", HttpStatus.FORBIDDEN.value(),
                        "message", "You can't view this page"
                )
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessException(AccessDeniedException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                Map.of(
                        "status", HttpStatus.FORBIDDEN.value(),
                        "message", "You can't view this page"
                )
        );
    }
}
