package com.peluware.springframework.crud.jpa.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FooDto {

    @NotNull
    @NotEmpty
    private String name;

    private String description;

    private String email;

    private LocalDate date;

}
