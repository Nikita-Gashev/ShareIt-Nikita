package ru.yandex.practicum.shareit.controller;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.shareit.data.dto.ErrorResponse;
import ru.yandex.practicum.shareit.data.exception.ItemNotFoundException;
import ru.yandex.practicum.shareit.data.exception.NotAvailableEmailException;
import ru.yandex.practicum.shareit.data.exception.NotAvailableOwnerException;
import ru.yandex.practicum.shareit.data.exception.UserNotFoundException;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<ErrorResponse> errorResponses = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorResponse(error.getField(), error.getDefaultMessage()))
                .toList();
        log.warn(e.getMessage());
        return new ErrorResponse("ValidationException",
                errorResponses.toString()
        );

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
        final List<ErrorResponse> errorResponses = e.getConstraintViolations().stream()
                .map(violation -> new ErrorResponse(violation.getPropertyPath().toString(), violation.getMessage()))
                .toList();
        log.warn(e.getMessage());
        return new ErrorResponse("ValidationException",
                errorResponses.toString()
        );
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
