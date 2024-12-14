package ru.yandex.practicum.shareit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareit.data.dto.ItemDto;
import ru.yandex.practicum.shareit.data.exception.ItemNotFoundException;
import ru.yandex.practicum.shareit.data.exception.NotAvailableOwnerException;
import ru.yandex.practicum.shareit.data.model.Item;
import ru.yandex.practicum.shareit.data.model.User;
import ru.yandex.practicum.shareit.mapper.ItemMapper;
import ru.yandex.practicum.shareit.mapper.UserMapper;
import ru.yandex.practicum.shareit.service.ItemService;
import ru.yandex.practicum.shareit.service.UserService;
import ru.yandex.practicum.shareit.storage.ItemStorage;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceInMemory implements ItemService {
    private final ItemStorage itemStorage;
    private final ItemMapper itemMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public ItemDto add(ItemDto itemDto, int userId) {
        Item item = itemMapper.toItem(itemDto);
        User owner = userMapper.toUser(userService.getById(userId));
        item.setOwner(owner);
        itemStorage.add(item, userId);
        log.info("Item '{}' added", item.getName());
        return itemMapper.toItemDto(item);
    }

    @Override
    public ItemDto update(ItemDto itemDto, int userId, int itemId) {
        Item item = createExistItem(itemDto, userId, itemId);
        itemStorage.update(item, userId, itemId);
        log.info("Item '{}' update", item.getName());
        return itemMapper.toItemDto(item);
    }

    private Item createExistItem(ItemDto itemDto, int userId, int itemId) {
        Item item = itemStorage.getById(itemId).orElseThrow(() -> new ItemNotFoundException("Incorrect id"));
        if (!(item.getOwner().getId() == userId)) {
            throw new NotAvailableOwnerException("User not owner");
        }
        Item itemForUpdate = itemMapper.toItem(itemDto);
        if (itemForUpdate.getName() != null) {
            item.setName(itemForUpdate.getName());
        }
        if (itemForUpdate.getDescription() != null) {
            item.setDescription(itemForUpdate.getDescription());
        }
        item.setAvailable(itemForUpdate.isAvailable());
        return item;
    }

    @Override
    public ItemDto getById(int itemId) {
        Item item = itemStorage.getById(itemId).orElseThrow(() -> new ItemNotFoundException("Incorrect id"));
        log.info("Get item with id: {}", itemId);
        return itemMapper.toItemDto(item);
    }

    @Override
    public List<ItemDto> getByOwnerId(int userId) {
        log.info("Get items by owner with id: {}", userId);
        return itemStorage.getByOwnerId(userId).stream()
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> search(String text) {
        if (text.isBlank()) {
            return Collections.emptyList();
        }
        List<Item> itemForSearch = itemStorage.getAllItems().stream()
                .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase())
                        || item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .filter(Item::isAvailable)
                .toList();
        log.info("Search item with {}", text);
        return itemForSearch.stream()
                .map(itemMapper::toItemDto)
                .toList();
    }
}
