package ru.yandex.practicum.shareit.data.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "users", schema = "public")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;
}
