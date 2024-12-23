package ru.yandex.practicum.shareit.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.shareit.data.model.Item;

import java.util.List;

public interface ItemStorage extends JpaRepository<Item, Integer> {

    @Query("""
            SELECT i
            FROM Item AS i
            JOIN i.owner AS o
            WHERE o.id = ?1
            """)
    List<Item> findAllItemsByOwnerId(int userId);

    @Query("""
            SELECT i
            FROM Item AS i
            WHERE i.available = true
            AND (i.name ILIKE CONCAT('%', ?1, '%')
            OR i.description ILIKE CONCAT('%', ?1, '%'))
            """)
    List<Item> findTextInNameAndDescriptionItem(String text);

    @Query("""
            SELECT i
            FROM Item AS i
            JOIN FETCH i.owner
            WHERE i.id = ?1
            """)
    Item findItemWithOwner(int itemId);
}
