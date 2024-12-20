package ru.yandex.practicum.shareit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareit.data.dto.UserDto;
import ru.yandex.practicum.shareit.data.exception.NotAvailableEmailException;
import ru.yandex.practicum.shareit.data.exception.UserNotFoundException;
import ru.yandex.practicum.shareit.data.model.User;
import ru.yandex.practicum.shareit.mapper.UserMapper;
import ru.yandex.practicum.shareit.service.UserService;
import ru.yandex.practicum.shareit.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceInMemory implements UserService {
    private final UserStorage userStorage;
    private final UserMapper userMapper;

    @Override
    public UserDto add(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        isEmailAvailable(user.getEmail());
        User userWithId = userStorage.add(user);
        log.info("User '{}' added", user.getEmail());
        return userMapper.toUserDto(userWithId);
    }

    @Override
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

    @Override
    public void delete(int userId) {
        getById(userId);
        userStorage.delete(userId);
        log.info("Delete user with id: {}", userId);
    }

    @Override
    public UserDto getById(int userId) {
        User user = userStorage.getById(userId).orElseThrow(() -> new UserNotFoundException("Incorrect id"));
        log.info("Get user with id: {}", userId);
        return userMapper.toUserDto(user);
    }

    @Override
    public List<User> getAll() {
        return userStorage.getAll();
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
