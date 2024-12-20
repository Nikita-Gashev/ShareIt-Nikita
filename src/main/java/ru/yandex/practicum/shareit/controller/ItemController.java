package ru.yandex.practicum.shareit.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shareit.data.dto.ItemDto;
import ru.yandex.practicum.shareit.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/items")
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto add(@Valid @RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") int userId) {
        log.info("Request received POST /items");
        return itemService.add(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") int userId,
                          @PathVariable int itemId) {
        log.info("Request received PATCH /items/{}", itemId);
        return itemService.update(itemDto, userId, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemDto update(@PathVariable int itemId) {
        log.info("Request received GET /items/{}", itemId);
        return itemService.getById(itemId);
    }

    @GetMapping
    public List<ItemDto> getByOwnerId(@RequestHeader("X-Sharer-User-Id") int userId) {
        log.info("Request received GET /items");
        return itemService.getByOwnerId(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam String text) {
        log.info("Request received GET /items/search?text={}", text);
        return itemService.search(text);
    }
}
