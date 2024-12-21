package ru.yandex.practicum.shareit.storage.impl;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.shareit.data.model.User;
import ru.yandex.practicum.shareit.storage.UserStorage;

import java.util.*;

@Repository
public class UserStorageInMemory implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 0;

    @Override
    public User add(User user) {
        id++;
        user.setId(id);
        users.put(id, user);
        return user;
    }

    @Override
    public User update(int userId, User user) {
        users.put(userId, user);
        return user;
    }

    @Override
    public void delete(int userId) {
        users.remove(userId);

    }

    @Override
    public Optional<User> getById(int userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public List<User> getAll() {
        if (users.isEmpty()) {
            return Collections.emptyList();
        }
        Collection<User> values = users.values();
        return new ArrayList<>(values);
    }
}
