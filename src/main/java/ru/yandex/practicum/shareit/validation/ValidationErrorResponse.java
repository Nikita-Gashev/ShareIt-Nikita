package ru.yandex.practicum.shareit.validation;

import java.util.List;

public record ValidationErrorResponse(List<Violation> violations) {
}

