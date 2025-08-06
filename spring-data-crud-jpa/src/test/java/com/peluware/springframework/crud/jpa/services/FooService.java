package com.peluware.springframework.crud.jpa.services;

import com.peluware.springframework.crud.jpa.JpaCrudService;
import com.peluware.springframework.crud.jpa.models.Foo;
import com.peluware.springframework.crud.jpa.dto.FooDto;
import com.peluware.springframework.crud.jpa.repositories.FooRepostory;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
public class FooService implements JpaCrudService<Foo, FooDto, Long, FooRepostory> {

    private final FooRepostory repository;
    private final EntityManager entityManager;

    @Override
    public void mapModel(FooDto dto, Foo model) {
        model.setName(dto.getName());
        model.setDescription(dto.getDescription());
        model.setEmail(dto.getEmail());
        model.setDate(dto.getDate());
    }

    @Override
    public Class<Foo> getEntityClass() {
        return Foo.class;
    }
}
