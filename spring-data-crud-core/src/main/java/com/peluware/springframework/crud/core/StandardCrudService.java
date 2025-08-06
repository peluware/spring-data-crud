package com.peluware.springframework.crud.core;

import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Standard implementation of a CRUD service for CRUD operations,
 * leveraging Spring Data repositories.
 * <p>
 * Provides default behaviors by extending standard read and write service interfaces.
 * </p>
 *
 * @param <E>  the entity type extending {@link Persistable} with identifier of type {@code ID}
 * @param <D>  the DTO type used for write operations
 * @param <ID> the type of the entity identifier
 * @param <R>  the repository type extending both {@link ListCrudRepository} and {@link PagingAndSortingRepository}
 */
public interface StandardCrudService<
        E extends Persistable<ID>,
        D,
        ID,
        R extends ListCrudRepository<E, ID> & PagingAndSortingRepository<E, ID>>
        extends StandardReadService<E, ID, R>,
        StandardWriteService<E, D, ID, R>,
        CrudService<E, D, ID> {

    /**
     * {@inheritDoc}
     * <p>
     * Explicitly delegates to {@link StandardReadService#internalFind(Object)} to
     * resolve ambiguity from multiple inheritance.
     * </p>
     */
    @Override
    default E internalFind(ID id) {
        return StandardReadService.super.internalFind(id);
    }
}
