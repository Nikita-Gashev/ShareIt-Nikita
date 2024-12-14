package ru.yandex.practicum.shareit.controller;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.shareit.data.exception.ItemNotFoundException;
import ru.yandex.practicum.shareit.data.exception.NotAvailableEmailException;
import ru.yandex.practicum.shareit.data.exception.NotAvailableOwnerException;
import ru.yandex.practicum.shareit.data.exception.UserNotFoundException;
import ru.yandex.practicum.shareit.util.ErrorResponse;
import ru.yandex.practicum.shareit.validation.ValidationErrorResponse;
import ru.yandex.practicum.shareit.validation.Violation;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .toList();
        log.warn(violations.toString());
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(violation -> new Violation(violation.getPropertyPath().toString(), violation.getMessage()))
                .toList();
        log.warn(violations.toString());
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleNotAvailableEmailException(NotAvailableEmailException e) {
        log.warn(e.getMessage());
        return new ErrorResponse("NotAvailableEmailException",
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException e) {
        log.warn(e.getMessage());
        return new ErrorResponse("UserNotFoundException",
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleItemNotFoundException(ItemNotFoundException e) {
        log.warn(e.getMessage());
        return new ErrorResponse("ItemNotFoundException",
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotAvailableOwnerException(NotAvailableOwnerException e) {
        log.warn(e.getMessage());
        return new ErrorResponse("NotAvailableEmailException",
                e.getMessage()
        );
    }
}
