package ru.yandex.practicum.shareit.data.model;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String email;
}