package ru.yandex.practicum.shareit.data.dto;

import lombok.Data;
import ru.yandex.practicum.shareit.data.enums.BookingStatus;
import ru.yandex.practicum.shareit.data.model.Item;
import ru.yandex.practicum.shareit.data.model.User;

import java.time.LocalDateTime;

@Data
public class BookingDto {
    private int id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Item item;
    private User booker;
    private BookingStatus status;
}
