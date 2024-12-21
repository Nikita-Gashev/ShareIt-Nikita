package ru.yandex.practicum.shareit.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.shareit.data.model.Comment;

import java.util.List;

public interface CommentStorage extends JpaRepository<Comment, Integer> {
    @Query("""
            SELECT c
            FROM Comment AS c
            JOIN FETCH c.item AS i
            WHERE i.id = ?1
            ORDER BY c.created ASC
            """)
    List<Comment> findByItemId(int itemId);
}
