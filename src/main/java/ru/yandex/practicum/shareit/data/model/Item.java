package ru.yandex.practicum.shareit.data.model;

import lombok.Data;

@Data
public class Item {
    private int id;
    private String name;
    private String description;
    private boolean available;
    private User owner;
    private Request request;
}
