package com.peluware.springframework.crud.jpa.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;

@Data
@Entity
public class Foo implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String email;

    @Column(nullable = false)
    private LocalDate date;

    @Override
    public boolean isNew() {
        return id == null;
    }
}
