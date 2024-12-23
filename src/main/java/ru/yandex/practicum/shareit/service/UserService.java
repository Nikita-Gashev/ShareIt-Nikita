package ru.yandex.practicum.shareit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareit.data.dto.UserDto;
import ru.yandex.practicum.shareit.data.exception.NotAvailableEmailException;
import ru.yandex.practicum.shareit.data.exception.UserNotFoundException;
import ru.yandex.practicum.shareit.data.model.User;
import ru.yandex.practicum.shareit.mapper.UserMapper;
import ru.yandex.practicum.shareit.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;
    private final UserMapper userMapper;

    public UserDto add(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        isEmailAvailable(user.getEmail());
        User userWithId = userStorage.save(user);
        log.info("User '{}' added", user.getEmail());
        return userMapper.toUserDto(userWithId);
    }

    public UserDto update(int userId, UserDto userDto) {
        UserDto userFromStorage = getById(userId);
        User user = userMapper.toUser(userFromStorage);
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            isEmailAvailable(userDto.getEmail());
            user.setEmail(userDto.getEmail());
        }
        log.info("User '{}' update", user.getEmail());
        return userMapper.toUserDto(user);
    }

    public void delete(int userId) {
        User user = userMapper.toUser(getById(userId));
        userStorage.delete(user);
        log.info("Delete user with id: {}", userId);
    }

    public UserDto getById(int userId) {
        User user = userStorage.findById(userId).orElseThrow(() -> new UserNotFoundException("Incorrect id"));
        log.info("Get user with id: {}", userId);
        return userMapper.toUserDto(user);
    }

    public List<User> getAll() {
        return userStorage.findAll();
    }

    private void isEmailAvailable(String email) {
        if (getAll().isEmpty()) {
            return;
        }
        List<String> usersEmail = new ArrayList<>();
        getAll().forEach(userForEmail -> usersEmail.add(userForEmail.getEmail()));
        if (usersEmail.contains(email)) {
            throw new NotAvailableEmailException("Email not available");
        }
    }
}
