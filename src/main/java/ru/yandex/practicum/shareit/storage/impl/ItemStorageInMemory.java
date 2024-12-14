package ru.yandex.practicum.shareit.storage.impl;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.shareit.data.model.Item;
import ru.yandex.practicum.shareit.storage.ItemStorage;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ItemStorageInMemory implements ItemStorage {
    private final Map<Integer, List<Item>> items = new HashMap<>();

    @Override
    public Item add(Item item, int userId) {
        item.setId(getId());
        items.compute(userId, (ownerId, userItems) -> {
            if (userItems == null) {
                userItems = new ArrayList<>();
            }
            userItems.add(item);
            return userItems;
        });
        return item;
    }

    @Override
    public Item update(Item item, int userId, int itemId) {
        List<Item> userItems = new ArrayList<>(items.get(userId).stream()
                .filter(itemFromStream -> itemFromStream.getId() != itemId)
                .toList());
        userItems.add(item);
        items.put(userId, userItems);
        return item;
    }

    @Override
    public Optional<Item> getById(int itemId) {
        return items.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(itemWithId -> itemWithId.getId() == itemId)
                .findAny();
    }

    @Override
    public List<Item> getByOwnerId(int userId) {
        return items.getOrDefault(userId, Collections.emptyList());
    }

    @Override
    public List<Item> getAllItems() {
        if (items.isEmpty()) {
            return Collections.emptyList();
        }
        return items.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private int getId() {
        int lastId = items.values()
                .stream()
                .flatMap(Collection::stream)
                .mapToInt(Item::getId)
                .max()
                .orElse(0);
        return lastId + 1;
    }
}
