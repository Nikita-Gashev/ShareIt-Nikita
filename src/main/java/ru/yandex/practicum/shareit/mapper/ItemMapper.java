package ru.yandex.practicum.shareit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.shareit.data.dto.ItemDto;
import ru.yandex.practicum.shareit.data.dto.ItemWithDateDto;
import ru.yandex.practicum.shareit.data.model.Item;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {
    Item toItem(ItemDto itemDto);

    ItemDto toItemDto(Item item);

    ItemWithDateDto toItemWithDate(Item item);
}
