package ru.yandex.practicum.shareit.data.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemRequest {
    private int id;
    private String description;
    private User requester;
    private LocalDateTime created;
}
