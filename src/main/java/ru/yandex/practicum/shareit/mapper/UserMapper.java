package ru.yandex.practicum.shareit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.shareit.data.dto.UserDto;
import ru.yandex.practicum.shareit.data.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toUser(UserDto userDto);

    UserDto toUserDto(User user);
}
