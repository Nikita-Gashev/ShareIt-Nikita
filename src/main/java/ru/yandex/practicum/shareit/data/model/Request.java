package ru.yandex.practicum.shareit.data.model;

import lombok.Data;

@Data
public class Request {
    private int id;
    private String description;
    private User requester;
}
