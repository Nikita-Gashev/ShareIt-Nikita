package ru.yandex.practicum.shareit.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemDto {
    private int id;
    @NotBlank(message = "Name should be not empty")
    private String name;
    @NotBlank(message = "Description should be not empty")
    private String description;
    @NotNull(message = "Available should be filled")
    private Boolean available;
}
