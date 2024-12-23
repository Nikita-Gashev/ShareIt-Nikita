package ru.yandex.practicum.shareit.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.shareit.data.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingStorage extends JpaRepository<Booking, Integer> {
    @Query("""
            SELECT b
            FROM Booking AS b
            JOIN FETCH b.booker
            JOIN FETCH b.item AS i
            JOIN FETCH i.owner
            WHERE b.id = ?1
            """)
    Booking findBookingWithItemAndBooker(int bookingId);

    @Query("""
            SELECT b
            FROM Booking AS b
            JOIN FETCH b.booker AS bo
            JOIN FETCH b.item AS i
            JOIN FETCH i.owner
            WHERE bo.id = ?1
            ORDER BY b.start ASC
            """)
    List<Booking> findForUserByStateAll(int userId);

    @Query("""
            SELECT b
            FROM Booking AS b
            JOIN FETCH b.booker AS bo
            JOIN FETCH b.item AS i
            JOIN FETCH i.owner
            WHERE bo.id = ?1
            AND ?2 BETWEEN b.start AND b.end
            ORDER BY b.start ASC
            """)
    List<Booking> findForUserByStateCurrent(int userId, LocalDateTime now);

    @Query("""
            SELECT b
            FROM Booking AS b
            JOIN FETCH b.booker AS bo
            JOIN FETCH b.item AS i
            JOIN FETCH i.owner
            WHERE bo.id = ?1
            AND ?2 > b.end
            ORDER BY b.start ASC
            """)
    List<Booking> findForUserByStatePast(int userId, LocalDateTime now);

    @Query("""
            SELECT b
            FROM Booking AS b
            JOIN FETCH b.booker AS bo
            JOIN FETCH b.item AS i
            JOIN FETCH i.owner
            WHERE bo.id = ?1
            AND ?2 < b.start
            ORDER BY b.start ASC
            """)
    List<Booking> findForUserByStateFuture(int userId, LocalDateTime now);

    @Query("""
            SELECT b
            FROM Booking AS b
            JOIN FETCH b.booker AS bo
            JOIN FETCH b.item AS i
            JOIN FETCH i.owner
            WHERE bo.id = ?1
            AND b.status ILIKE ?2
            ORDER BY b.start ASC
            """)
    List<Booking> findForUserByState(int userId, String state);

    @Query("""
            SELECT b
            FROM Booking AS b
            JOIN FETCH b.booker AS bo
            JOIN FETCH b.item AS i
            JOIN FETCH i.owner AS ow
            WHERE ow.id = ?1
            ORDER BY b.start ASC
            """)
    List<Booking> findForItemsByStateAll(int userId);

    @Query("""
            SELECT b
            FROM Booking AS b
            JOIN FETCH b.booker AS bo
            JOIN FETCH b.item AS i
            JOIN FETCH i.owner AS ow
            WHERE ow.id = ?1
            AND ?2 BETWEEN b.start AND b.end
            ORDER BY b.start ASC
            """)
    List<Booking> findForItemsByStateCurrent(int userId, LocalDateTime now);

    @Query("""
            SELECT b
            FROM Booking AS b
            JOIN FETCH b.booker AS bo
            JOIN FETCH b.item AS i
            JOIN FETCH i.owner AS ow
            WHERE ow.id = ?1
            AND ?2 > b.end
            ORDER BY b.start ASC
            """)
    List<Booking> findForItemsByStatePast(int userId, LocalDateTime now);

    @Query("""
            SELECT b
            FROM Booking AS b
            JOIN FETCH b.booker AS bo
            JOIN FETCH b.item AS i
            JOIN FETCH i.owner AS ow
            WHERE ow.id = ?1
            AND ?2 < b.start
            ORDER BY b.start ASC
            """)
    List<Booking> findForItemsByStateFuture(int userId, LocalDateTime now);

    @Query("""
            SELECT b
            FROM Booking AS b
            JOIN FETCH b.booker AS bo
            JOIN FETCH b.item AS i
            JOIN FETCH i.owner AS ow
            WHERE ow.id = ?1
            AND b.status ILIKE ?2
            ORDER BY b.start ASC
            """)
    List<Booking> findForItemsByState(int userId, String state);

    @Query("""
            SELECT b
            FROM Booking AS b
            JOIN FETCH b.booker AS bo
            JOIN FETCH b.item AS i
            JOIN FETCH i.owner AS ow
            WHERE i.id = ?1
            ORDER BY b.start ASC
            """)
    List<Booking> findByItemId(int itemId);

    @Query("""
            SELECT b
            FROM Booking AS b
            JOIN FETCH b.booker AS bo
            JOIN FETCH b.item AS i
            JOIN FETCH i.owner AS ow
            WHERE i.id = ?1
            AND bo.id = ?2
            """)
    Booking findByItemIdAndBookerId(int itemId, int bookerId);
}
