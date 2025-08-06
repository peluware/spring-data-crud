package com.peluware.springframework.crud.core;

import com.peluware.springframework.crud.core.providers.RepositoryProvider;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Standard implementation of a write service for CRUD operations,
 * leveraging Spring Data repositories.
 * <p>
 * Provides default implementations for create, update, and delete operations
 * using a repository that combines {@link ListCrudRepository} and
 * {@link PagingAndSortingRepository}.
 * </p>
 *
 * @param <E>  the entity type that extends {@link Persistable} with an identifier of type {@code ID}
 * @param <D>  the DTO type used for mapping data to the entity
 * @param <ID> the type of the entity identifier
 * @param <R>  the repository type that extends both {@link ListCrudRepository} and {@link PagingAndSortingRepository}
 */
public interface StandardWriteService<
        E extends Persistable<ID>,
        D,
        ID,
        R extends ListCrudRepository<E, ID> & PagingAndSortingRepository<E, ID>>
        extends WriteService<E, D, ID>, RepositoryProvider<R> {

    /**
     * {@inheritDoc}
     */
    @Override
    default void internalCreate(E entity) {
        getRepository().save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void internalUpdate(E entity) {
        getRepository().save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void internalDelete(E entity) {
        getRepository().delete(entity);
    }
}
