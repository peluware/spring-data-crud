package com.peluware.springframework.crud.core.web.controllers;

import com.peluware.springframework.crud.core.CrudService;
import org.springframework.data.domain.Persistable;

/**
 * CRUD Controller for performing both read and write operations.
 * <p>
 * This controller combines both read and write operations. It provides endpoints for creating, updating, deleting,
 * and retrieving entities. It uses the services defined in {@link CrudService} for delegating the actual operations.
 * </p>
 *
 * @param <M>  Entity model, which extends {@link Persistable} with an ID type of {@code ID}
 * @param <D>  DTO (Data Transfer Object) used to transfer data for create and update operations
 * @param <ID> Type of the entity's identifier (e.g., {@link Long}, {@link String})
 * @param <S>  Service that extends {@link CrudService} for the CRUD operations
 */
public interface CrudController<M extends Persistable<ID>, D, ID> extends
        WriteController<M, D, ID>,
        ReadController<M, ID> {

    @Override
    CrudService<M, D, ID> getService();
}
