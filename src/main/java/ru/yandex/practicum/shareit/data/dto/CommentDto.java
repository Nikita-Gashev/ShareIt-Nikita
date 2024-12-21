package ru.yandex.practicum.shareit.data.dto;

import lombok.Data;
import ru.yandex.practicum.shareit.data.model.Item;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private int id;
    private String text;
    private Item item;
    private String authorName;
    private LocalDateTime created;
}
