package ru.yandex.practicum.shareit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareit.data.dto.BookingDto;
import ru.yandex.practicum.shareit.data.dto.BookingFromRequestDto;
import ru.yandex.practicum.shareit.data.enums.BookingStatus;
import ru.yandex.practicum.shareit.data.exception.*;
import ru.yandex.practicum.shareit.data.model.Booking;
import ru.yandex.practicum.shareit.data.model.Item;
import ru.yandex.practicum.shareit.data.model.User;
import ru.yandex.practicum.shareit.mapper.BookingMapper;
import ru.yandex.practicum.shareit.storage.BookingStorage;
import ru.yandex.practicum.shareit.storage.ItemStorage;
import ru.yandex.practicum.shareit.storage.UserStorage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingService {
    private final BookingStorage bookingStorage;
    private final BookingMapper bookingMapper;
    private final UserStorage userStorage;
    private final ItemStorage itemStorage;

    public BookingDto add(int userId, BookingFromRequestDto bookingFromRequestDto) {
        if (!bookingFromRequestDto.getStart().isBefore(bookingFromRequestDto.getEnd())) {
            throw new ValidationException("Start time should be before end time");
        }
        Booking booking = bookingMapper.toBookingFromRequest(bookingFromRequestDto);
        User booker = userStorage.findById(userId).orElseThrow(() -> new UserNotFoundException("Incorrect booker id"));
        booking.setBooker(booker);
        int itemId = bookingFromRequestDto.getItemId();
        Item item = itemStorage.findItemWithOwner(itemId);
        if (item == null) {
            throw new ItemNotFoundException("Incorrect item id");
        }
        if (!item.isAvailable()) {
            throw new ValidationException("Item not available");
        }
        booking.setItem(item);
        booking.setStatus(BookingStatus.WAITING);
        bookingStorage.save(booking);
        log.info("Booking '{}' added", booking.getId());
        return bookingMapper.toBookingDto(booking);
    }

    public BookingDto updateStatus(int userId, int bookingId, boolean approved) {
        Booking booking = getBooking(bookingId);
        User owner = booking.getItem().getOwner();
        if (owner.getId() != userId) {
            throw new NotAvailableOwnerException("User not owner");
        }
        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        bookingStorage.save(booking);
        log.info("Booking status update to '{}'", booking.getStatus());
        return bookingMapper.toBookingDto(booking);
    }

    public BookingDto getById(int userId, int bookingId) {
        Booking booking = getBooking(bookingId);
        User booker = booking.getBooker();
        Item item = booking.getItem();
        User owner = item.getOwner();
        if (booker.getId() != userId && owner.getId() != userId) {
            throw new NotAvailableOwnerException("Not owner or booker");
        }
        log.info("Booking '{}' get", bookingId);
        return bookingMapper.toBookingDto(booking);
    }

    public List<BookingDto> getAllByState(int userId, String state) {
        LocalDateTime now = LocalDateTime.now();
        List<Booking> bookingList = new ArrayList<>();
        bookingList = switch (state) {
            case "ALL" -> bookingStorage.findForUserByStateAll(userId);
            case "CURRENT" -> bookingStorage.findForUserByStateCurrent(userId, now);
            case "PAST" -> bookingStorage.findForUserByStatePast(userId, now);
            case "FUTURE" -> bookingStorage.findForUserByStateFuture(userId, now);
            case "WAITING", "REJECTED" -> bookingStorage.findForUserByState(userId, state);
            default -> bookingList;
        };
        if (bookingList.isEmpty()) {
            log.info("No booking for user '{}' with state: {}", userId, state);
            return Collections.emptyList();
        }
        log.info("Get booking list for user '{}' with state: {}", userId, state);
        return bookingMapper.toBookingDto(bookingList);
    }

    public List<BookingDto> getAllForOwnerItems(int userId, String state) {
        if (!userStorage.existsById(userId)) {
            throw new UserNotFoundException("Incorrect user id");
        }
        LocalDateTime now = LocalDateTime.now();
        List<Booking> bookingList = new ArrayList<>();
        bookingList = switch (state) {
            case "ALL" -> bookingStorage.findForItemsByStateAll(userId);
            case "CURRENT" -> bookingStorage.findForItemsByStateCurrent(userId, now);
            case "PAST" -> bookingStorage.findForItemsByStatePast(userId, now);
            case "FUTURE" -> bookingStorage.findForItemsByStateFuture(userId, now);
            case "WAITING", "REJECTED" -> bookingStorage.findForItemsByState(userId, state);
            default -> bookingList;
        };
        if (bookingList.isEmpty()) {
            log.info("No booking of items for user '{}' with state: {}", userId, state);
            return Collections.emptyList();
        }
        log.info("Get booking list of items for user '{}' with state: {}", userId, state);
        return bookingMapper.toBookingDto(bookingList);
    }

    private Booking getBooking(int bookingId) {
        if (!bookingStorage.existsById(bookingId)) {
            throw new BookingNotFoundException("Incorrect booking id");
        }
        return bookingStorage.findBookingWithItemAndBooker(bookingId);
    }
}
