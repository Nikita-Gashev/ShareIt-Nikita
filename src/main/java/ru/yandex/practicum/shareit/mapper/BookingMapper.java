package ru.yandex.practicum.shareit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.shareit.data.dto.BookingDto;
import ru.yandex.practicum.shareit.data.dto.BookingFromRequestDto;
import ru.yandex.practicum.shareit.data.dto.BookingWithDateDto;
import ru.yandex.practicum.shareit.data.model.Booking;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingMapper {
    Booking toBookingFromDto(BookingDto bookingDto);

    Booking toBookingFromRequest(BookingFromRequestDto bookingFromRequestDto);

    BookingDto toBookingDto(Booking booking);

    List<BookingDto> toBookingDto(List<Booking> bookingList);

    BookingWithDateDto toBookingWithDate(Booking booking);
}
