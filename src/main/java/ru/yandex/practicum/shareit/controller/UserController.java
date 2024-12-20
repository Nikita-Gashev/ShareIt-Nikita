package ru.yandex.practicum.shareit.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shareit.data.dto.UserDto;
import ru.yandex.practicum.shareit.service.UserService;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDto add(@Valid @RequestBody UserDto userDto) {
        log.info("Request received POST /users");
        return userService.add(userDto);
    }

    @PatchMapping("/{id}")
    public UserDto update(@PathVariable int id, @RequestBody UserDto userDto) {
        log.info("Request received PATCH /users/{}", id);
        return userService.update(id, userDto);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable int id) {
        log.info("Request received GET /users/{}", id);
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        log.info("Request received DELETE /users/{}", id);
        userService.delete(id);
    }
}
