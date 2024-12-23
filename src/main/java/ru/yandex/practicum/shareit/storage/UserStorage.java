package ru.yandex.practicum.shareit.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.shareit.data.model.User;

public interface UserStorage extends JpaRepository<User, Integer> {
}
