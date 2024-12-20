package ru.yandex.practicum.shareit.service;

import ru.yandex.practicum.shareit.data.dto.UserDto;
import ru.yandex.practicum.shareit.data.model.User;

import java.util.List;

public interface UserService {
    UserDto add(UserDto userDto);

    UserDto update(int userId, UserDto userDto);

    void delete(int userId);

    UserDto getById(int userId);

    List<User> getAll();
}
