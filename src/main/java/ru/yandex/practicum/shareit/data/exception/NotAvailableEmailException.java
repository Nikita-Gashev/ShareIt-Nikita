package ru.yandex.practicum.shareit.data.exception;

public class NotAvailableEmailException extends RuntimeException {
    public NotAvailableEmailException(String message) {
        super(message);
    }
}

