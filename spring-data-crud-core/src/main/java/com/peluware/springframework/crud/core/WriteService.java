package com.peluware.springframework.crud.core;

import com.peluware.springframework.crud.core.exceptions.NotFoundEntityException;
import com.peluware.springframework.crud.core.providers.EntityClassProvider;
import com.peluware.springframework.crud.core.providers.TransactionOperationsProvider;
import com.peluware.springframework.crud.core.hooks.WriteHooks;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import org.springframework.data.domain.Persistable;
import org.springframework.validation.annotation.Validated;

/**
 * Defines a generic interface for write operations in a CRUD service.
 * <p>
 * Supports creation, update, and deletion of entities, using hooks for interception
 * and transaction handling via {@link TransactionOperationsProvider}.
 *
 * @param <E>  the entity type, must implement {@link Persistable}
 * @param <D>  the DTO type used to map data to the entity
 * @param <ID> the identifier type of the entity
 */
@Validated
public non-sealed interface WriteService<E extends Persistable<ID>, D, ID> extends
        Crud,
        EntityClassProvider<E>,
        TransactionOperationsProvider {

    /**
     * Gets the hooks associated with this write service.
     * Used for lifecycle interception (before/after create, update, delete).
     *
     * @return the write hooks
     */
    default WriteHooks<E, D, ID> getHooks() {
        return WriteHooks.getDefault();
    }

    /**
     * Creates a new entity instance from the provided DTO.
     * Executes before/after hooks and wraps the operation in a transaction.
     *
     * @param dto the data transfer object used to populate the new entity
     * @return the persisted entity
     */
    default E create(@Valid @NotNull D dto) {
        Crud.preProccess(this, CrudOperation.CREATE);

        var entity = newEntity();
        var hooks = getHooks();
        var transactionOperations = getTransactionOperations();

        return transactionOperations.execute(status -> {
            try {
                mapModel(dto, entity);
                hooks.onBeforeCreate(dto, entity);
                internalCreate(entity);
                hooks.onAfterCreate(dto, entity);
                return entity;
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
        });
    }

    /**
     * Updates an existing entity by ID using the given DTO.
     * Executes before/after hooks and wraps the operation in a transaction.
     *
     * @param id  the ID of the entity to update
     * @param dto the DTO containing updated data
     * @return the updated entity
     * @throws NotFoundEntityException if the entity is not found
     */
    default E update(@NotNull ID id, @Valid @NotNull D dto) throws NotFoundEntityException {
        Crud.preProccess(this, CrudOperation.UPDATE);

        var entity = internalFind(id);
        var hooks = getHooks();
        var transactionOperations = getTransactionOperations();

        return transactionOperations.execute(status -> {
            try {
                mapModel(dto, entity);
                hooks.onBeforeUpdate(dto, entity);
                internalUpdate(entity);
                hooks.onAfterUpdate(dto, entity);
                return entity;
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
        });
    }

    /**
     * Deletes an entity by ID.
     * Executes before/after hooks and does not require an explicit transaction boundary (unless added internally).
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundEntityException if the entity is not found
     */
    default void delete(@NotNull ID id) throws NotFoundEntityException {
        Crud.preProccess(this, CrudOperation.DELETE);

        var entity = internalFind(id);
        var hooks = getHooks();

        hooks.onBeforeDelete(entity);
        internalDelete(entity);
        hooks.onAfterDelete(entity);
    }

    /**
     * Instantiates a new entity using the default no-arg constructor.
     *
     * @return a new entity instance
     * @throws RuntimeException if instantiation fails
     */
    @SneakyThrows
    default E newEntity() {
        return getEntityClass().getConstructor().newInstance();
    }

    // --------- Abstract methods to be implemented ---------

    /**
     * Finds an entity by ID or throws an exception if not found.
     *
     * @param id the entity ID
     * @return the found entity
     * @throws NotFoundEntityException if the entity is not found
     */
    E internalFind(ID id) throws NotFoundEntityException;

    /**
     * Maps a DTO to an entity.
     * Typically used to update entity fields with DTO values before persisting.
     *
     * @param dto   the DTO with new data
     * @param model the entity to update
     */
    void mapModel(D dto, E model);

    /**
     * Persists a new entity in the data store.
     *
     * @param entity the entity to create
     */
    void internalCreate(E entity);

    /**
     * Updates an existing entity in the data store.
     *
     * @param entity the entity to update
     */
    void internalUpdate(E entity);

    /**
     * Deletes an existing entity from the data store.
     *
     * @param entity the entity to delete
     */
    void internalDelete(E entity);
}
