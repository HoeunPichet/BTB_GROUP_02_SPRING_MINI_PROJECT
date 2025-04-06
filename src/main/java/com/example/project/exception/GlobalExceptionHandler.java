package com.example.project.exception;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppNotFoundException.class)
    public ProblemDetail handleException(AppNotFoundException e) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detail.setDetail(e.getMessage());
        detail.setProperty("timestamp", LocalDateTime.now());

        return detail;
    }

    @ExceptionHandler(AppBadRequestException.class)
    public ProblemDetail handleBadRequestException(AppBadRequestException e) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setDetail(e.getMessage());
        detail.setProperty("timestamp", LocalDateTime.now());

        return detail;
    }

    @ExceptionHandler(ThrowFieldException.class)
    public ProblemDetail handleThrowFieldException(ThrowFieldException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put(e.getField(), e.getMessage());

        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setProperty("timestamp", LocalDateTime.now());
        detail.setDetail("BAD REQUEST");
        detail.setProperty("errors", errors);

        return detail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setProperty("timestamp", LocalDateTime.now());

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        detail.setDetail("BAD REQUEST");
        detail.setProperty("errors", errors);

        return detail;
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ProblemDetail handlerMethodValidationException(HandlerMethodValidationException e) {
        List<String> errors = new ArrayList<>();
        for (MessageSourceResolvable pathError : e.getAllErrors()) {
            errors.add(pathError.getDefaultMessage());
        }
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setDetail("BAD REQUEST");
        detail.setProperty("timestamp", LocalDateTime.now());
        detail.setProperty("errors", errors);

        return detail;
    }
    @ExceptionHandler(HandleEnumHabitFrequencyException.class)
    public ProblemDetail handleInvalidEnumValue(HandleEnumHabitFrequencyException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("frequency", ex.getMessage());
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setProperty("timestamp", LocalDateTime.now());
        detail.setProperty("errors", errors);
        return detail;
    }
}