package ru.yandex.practicum.shareit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareit.data.dto.CommentForItemDto;
import ru.yandex.practicum.shareit.data.dto.ItemDto;
import ru.yandex.practicum.shareit.data.dto.ItemWithDateDto;
import ru.yandex.practicum.shareit.data.exception.ItemNotFoundException;
import ru.yandex.practicum.shareit.data.exception.NotAvailableOwnerException;
import ru.yandex.practicum.shareit.data.model.Booking;
import ru.yandex.practicum.shareit.data.model.Comment;
import ru.yandex.practicum.shareit.data.model.Item;
import ru.yandex.practicum.shareit.data.model.User;
import ru.yandex.practicum.shareit.mapper.BookingMapper;
import ru.yandex.practicum.shareit.mapper.CommentMapper;
import ru.yandex.practicum.shareit.mapper.ItemMapper;
import ru.yandex.practicum.shareit.mapper.UserMapper;
import ru.yandex.practicum.shareit.storage.BookingStorage;
import ru.yandex.practicum.shareit.storage.CommentStorage;
import ru.yandex.practicum.shareit.storage.ItemStorage;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {
    private final ItemStorage itemStorage;
    private final ItemMapper itemMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final BookingStorage bookingStorage;
    private final BookingMapper bookingMapper;
    private final CommentStorage commentStorage;
    private final CommentMapper commentMapper;

    public ItemDto add(ItemDto itemDto, int userId) {
        Item item = itemMapper.toItem(itemDto);
        User owner = userMapper.toUser(userService.getById(userId));
        item.setOwner(owner);
        itemStorage.save(item);
        log.info("Item '{}' added", item.getName());
        return itemMapper.toItemDto(item);
    }

    public ItemDto update(ItemDto itemDto, int userId, int itemId) {
        Item item = createExistItem(itemDto, userId, itemId);
        itemStorage.save(item);
        log.info("Item '{}' update", item.getName());
        return itemMapper.toItemDto(item);
    }

    private Item createExistItem(ItemDto itemDto, int userId, int itemId) {
        Item item = itemStorage.findById(itemId).orElseThrow(() -> new ItemNotFoundException("Incorrect id"));
        ;
        if (!(item.getOwner().getId() == userId)) {
            throw new NotAvailableOwnerException("User not owner");
        }
        Item itemForUpdate = itemMapper.toItem(itemDto);
        if (itemForUpdate.getName() != null) {
            item.setName(itemForUpdate.getName());
        }
        if (itemForUpdate.getDescription() != null) {
            item.setDescription(itemForUpdate.getDescription());
        }
        item.setAvailable(itemForUpdate.isAvailable());
        return item;
    }

    public ItemWithDateDto getById(int itemId) {
        Item item = itemStorage.findById(itemId).orElseThrow(() -> new ItemNotFoundException("Incorrect id"));
        ItemWithDateDto itemWithDateDto = itemMapper.toItemWithDate(item);
        setBookingWithDateDto(itemWithDateDto);
        setCommentsToItemDto(itemWithDateDto);
        log.info("Get item with id: {}", itemId);
        return itemWithDateDto;
    }

    private ItemWithDateDto setCommentsToItemDto(ItemWithDateDto itemWithDateDto) {
        List<Comment> comments = commentStorage.findByItemId(itemWithDateDto.getId());
        List<CommentForItemDto> commentsDto = commentMapper.toCommentDto(comments);
        itemWithDateDto.setComments(commentsDto);
        return itemWithDateDto;
    }

    public List<ItemWithDateDto> getByOwnerId(int userId) {
        if (itemStorage.findAllItemsByOwnerId(userId).isEmpty()) {
            return Collections.emptyList();
        }
        log.info("Get items by owner with id: {}", userId);
        return itemStorage.findAllItemsByOwnerId(userId).stream()
                .map(itemMapper::toItemWithDate)
                .map(this::setBookingWithDateDto)
                .map(this::setCommentsToItemDto)
                .collect(Collectors.toList());
    }

    private ItemWithDateDto setBookingWithDateDto(ItemWithDateDto item) {
        List<Booking> itemBookings = bookingStorage.findByItemId(item.getId());
        LocalDateTime now = LocalDateTime.now();
        Optional<Booking> bookingBefore = itemBookings.stream()
                .filter(booking -> booking.getStart().isBefore(now))
                .filter(booking -> booking.getEnd().isAfter(now))
                .max((t1, t2) -> t1.getEnd().compareTo(t2.getEnd()));
        Optional<Booking> bookingAfter = itemBookings.stream()
                .filter(booking -> booking.getStart().isAfter(now))
                .min((t1, t2) -> t1.getStart().compareTo(t2.getStart()));
        bookingBefore.ifPresent(b -> item.setLastBooking(bookingMapper.toBookingWithDate(b)));
        bookingAfter.ifPresent(b -> item.setNextBooking(bookingMapper.toBookingWithDate(b)));
        return item;
    }

    public List<ItemDto> search(String text) {
        if (text.isBlank()) {
            return Collections.emptyList();
        }
        log.info("Search item with {}", text);
        return itemStorage.findTextInNameAndDescriptionItem(text).stream()
                .map(itemMapper::toItemDto)
                .toList();
    }
}
