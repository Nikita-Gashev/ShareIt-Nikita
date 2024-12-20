package ru.yandex.practicum.shareit.data.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
    private int id;
    @NotBlank(message = "Name should be not empty")
    private String name;
    @NotNull(message = "Email should be not empty")
    @Email(message = "Incorrect email")
    private String email;
}
