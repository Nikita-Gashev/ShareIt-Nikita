package ru.yandex.practicum.shareit.data.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingWithDateDto {
    private int id;
    private LocalDateTime start;
    private LocalDateTime end;
}
