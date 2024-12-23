package ru.yandex.practicum.shareit.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shareit.data.dto.BookingDto;
import ru.yandex.practicum.shareit.data.dto.BookingFromRequestDto;
import ru.yandex.practicum.shareit.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@Slf4j
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDto add(@RequestHeader("X-Sharer-User-Id") int userId,
                          @RequestBody @Valid BookingFromRequestDto bookingDto) {
        log.info("Request received POST /bookings");
        return bookingService.add(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto updateStatus(@RequestHeader("X-Sharer-User-Id") int userId,
                                   @PathVariable int bookingId,
                                   @RequestParam Boolean approved) {
        log.info("Request received PATCH /bookings/{}?approved={}", bookingId, approved);
        return bookingService.updateStatus(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getById(@RequestHeader("X-Sharer-User-Id") int userId,
                              @PathVariable int bookingId) {
        log.info("Request received GET /bookings/{}", bookingId);
        return bookingService.getById(userId, bookingId);
    }

    @GetMapping
    public List<BookingDto> getAllByState(@RequestHeader("X-Sharer-User-Id") int userId,
                                          @RequestParam(defaultValue = "ALL") String state) {
        log.info("Request received GET /bookings?state={}", state);
        return bookingService.getAllByState(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllForOwnerItems(@RequestHeader("X-Sharer-User-Id") int userId,
                                                @RequestParam(defaultValue = "ALL") String state) {
        log.info("Request received GET /bookings/owner?state={}", state);
        return bookingService.getAllForOwnerItems(userId, state);
    }
}
