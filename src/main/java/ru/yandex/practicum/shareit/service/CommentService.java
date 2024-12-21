package ru.yandex.practicum.shareit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareit.data.dto.CommentDto;
import ru.yandex.practicum.shareit.data.dto.CommentFromRequestDto;
import ru.yandex.practicum.shareit.data.exception.BookingNotFoundException;
import ru.yandex.practicum.shareit.data.exception.UserNotFoundException;
import ru.yandex.practicum.shareit.data.exception.ValidationException;
import ru.yandex.practicum.shareit.data.model.Booking;
import ru.yandex.practicum.shareit.data.model.Comment;
import ru.yandex.practicum.shareit.data.model.Item;
import ru.yandex.practicum.shareit.data.model.User;
import ru.yandex.practicum.shareit.mapper.CommentMapper;
import ru.yandex.practicum.shareit.storage.BookingStorage;
import ru.yandex.practicum.shareit.storage.CommentStorage;
import ru.yandex.practicum.shareit.storage.ItemStorage;
import ru.yandex.practicum.shareit.storage.UserStorage;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentStorage commentStorage;
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;
    private final BookingStorage bookingStorage;
    private final CommentMapper commentMapper;

    public CommentDto add(CommentFromRequestDto commentFromRequestDto, int userId, int itemId) {
        Booking booking = bookingStorage.findByItemIdAndBookerId(itemId, userId);
        if (booking == null) {
            throw new BookingNotFoundException("Booking not found");
        }
        if (booking.getEnd().isAfter(LocalDateTime.now())) {
            throw new ValidationException("Booking not ended");
        }
        Comment comment = commentMapper.toComment(commentFromRequestDto);
        Item item = itemStorage.findItemWithOwner(itemId);
        comment.setItem(item);
        User author = userStorage.findById(userId).orElseThrow(() -> new UserNotFoundException("Incorrect booker id"));
        comment.setAuthor(author);
        LocalDateTime now = LocalDateTime.now();
        comment.setCreated(now);
        commentStorage.save(comment);
        CommentDto commentDto = commentMapper.toCommentDto(comment);
        commentDto.setAuthorName(author.getName());
        log.info("Comment '{}' added", comment.getId());
        return commentDto;
    }
}
