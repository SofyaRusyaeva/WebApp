package com.example.WebApp.exeption;

import jakarta.validation.ConstraintViolationException;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.rmi.AccessException;
import java.util.Map;
import java.util.stream.Collectors;

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

//    @ExceptionHandler(DuplicateException.class)
//    public ResponseEntity<Map<String, Object>> handleDuplicateBrandException(DuplicateException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                Map.of(
//                        "status", HttpStatus.BAD_REQUEST.value(),
//                        "message", e.getMessage()
//                )
//        );
//    }

    @ExceptionHandler(DuplicateException.class)
    public ModelAndView handleDuplicateBrandException(DuplicateException e) {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("error", e.getMessage());
        return modelAndView;
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
                        "message", "URL не найден"
                )
        );
    }

//    @ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class}) //, BadCredentialsException.class
//    public ResponseEntity<Map<String, Object>> handleEnumConversionError(Exception e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                Map.of(
//                        "status", HttpStatus.BAD_REQUEST.value(),
//                        "message", "Некорректные данные"
//                )
//        );
//    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCredentials(InvalidCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of(
                        "status", HttpStatus.UNAUTHORIZED.value(),
                        "message", e.getMessage()
                )
        );
    }

    @ExceptionHandler({AccessException.class, AccessDeniedException.class})
    public ResponseEntity<Map<String, Object>> handleAccessException(Exception e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                Map.of(
                        "status", HttpStatus.FORBIDDEN.value(),
                        "message", "You can't view this page"
                )
        );
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ModelAndView handleBadCredentials(BadCredentialsException e) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("error", "Неверный email или пароль");
        return modelAndView;
    }

    @ExceptionHandler(InvalidValueException.class)
    public ModelAndView handleBadCredentials(InvalidValueException e) {
        ModelAndView modelAndView = new ModelAndView(e.getViewName());
        modelAndView.addObject("error", e.getMessage());
        return modelAndView;
    }



//    @ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class, ConstraintViolationException.class})
//    public ModelAndView handleConstraintViolationException(Exception e) {
//        ModelAndView modelAndView = new ModelAndView("register");
//
//        if (e instanceof ConstraintViolationException) {
//            // Собираем все сообщения об ошибках в одну строку с полями, для более читабельного отображения
//            String errorMessages = ((ConstraintViolationException) e).getConstraintViolations().stream()
//                    .map(violation -> {
//                        String property = violation.getPropertyPath().toString(); // Имя поля
//                        String message = violation.getMessage(); // Сообщение ошибки
//                        return "Ошибка в поле '" + property + "': " + message; // Форматируем ошибку
//                    })
//                    .collect(Collectors.joining("<br>")); // Объединяем ошибки в одну строку с переносом
//
//            modelAndView.addObject("error", errorMessages);
//        } else {
//            modelAndView.addObject("error", "Произошла ошибка: " + e.getMessage());
//        }        return modelAndView;
//    }
}
