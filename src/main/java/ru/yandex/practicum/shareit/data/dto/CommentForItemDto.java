package ru.yandex.practicum.shareit.data.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentForItemDto {
    private int id;
    private String text;
    private LocalDateTime created;
}
