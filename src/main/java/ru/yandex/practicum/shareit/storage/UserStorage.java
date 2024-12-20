package ru.yandex.practicum.shareit.storage;

import ru.yandex.practicum.shareit.data.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    User add(User user);

    User update(int userId, User user);

    void delete(int userId);

    Optional<User> getById(int userId);

    List<User> getAll();
}
