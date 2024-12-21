package ru.yandex.practicum.shareit.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentFromRequestDto {
    @NotNull
    @NotBlank
    private String text;
}
