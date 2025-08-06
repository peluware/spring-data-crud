package com.peluware.springframework.crud.core.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class NotFoundEntityException extends RuntimeException {
    private final Class<?> modelClass;
    private final transient Object id;
}
