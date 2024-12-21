package ru.yandex.practicum.shareit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.shareit.data.dto.CommentDto;
import ru.yandex.practicum.shareit.data.dto.CommentForItemDto;
import ru.yandex.practicum.shareit.data.dto.CommentFromRequestDto;
import ru.yandex.practicum.shareit.data.model.Comment;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {
    Comment toComment(CommentFromRequestDto commentFromRequestDto);

    CommentDto toCommentDto(Comment comment);

    List<CommentForItemDto> toCommentDto(List<Comment> comments);
}
