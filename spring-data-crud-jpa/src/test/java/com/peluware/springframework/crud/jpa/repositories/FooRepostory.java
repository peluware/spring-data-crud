package com.peluware.springframework.crud.jpa.repositories;

import com.peluware.springframework.crud.jpa.models.Foo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FooRepostory extends JpaRepository<Foo, Long> {
}
