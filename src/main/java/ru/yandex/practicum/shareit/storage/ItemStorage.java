package ru.yandex.practicum.shareit.storage;

import ru.yandex.practicum.shareit.data.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemStorage {
    Item add(Item item, int userId);

    Item update(Item item, int userId, int itemId);

    Optional<Item> getById(int itemId);

    List<Item> getByOwnerId(int userId);

    List<Item> getAllItems();
}
