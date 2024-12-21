package ru.yandex.practicum.shareit.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class ItemWithDateDto {
    private int id;
    private String name;
    private String description;
    private Boolean available;
    private BookingWithDateDto lastBooking;
    private BookingWithDateDto nextBooking;
    private List<CommentForItemDto> comments;
}
