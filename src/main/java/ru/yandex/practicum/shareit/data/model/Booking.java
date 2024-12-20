package ru.yandex.practicum.shareit.data.model;

import lombok.Data;
import ru.yandex.practicum.shareit.data.enums.BookingStatus;

import java.time.LocalDate;

@Data
public class Booking {
    private int id;
    private LocalDate start;
    private LocalDate end;
    private Item item;
    private User booker;
    private BookingStatus status;
}
