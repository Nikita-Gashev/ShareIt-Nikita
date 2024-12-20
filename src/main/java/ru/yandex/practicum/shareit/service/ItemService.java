package ru.yandex.practicum.shareit.service;

import ru.yandex.practicum.shareit.data.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto add(ItemDto itemDto, int userId);

    ItemDto update(ItemDto itemDto, int userId, int itemId);

    ItemDto getById(int itemId);

    List<ItemDto> getByOwnerId(int userId);

    List<ItemDto> search(String text);
}
