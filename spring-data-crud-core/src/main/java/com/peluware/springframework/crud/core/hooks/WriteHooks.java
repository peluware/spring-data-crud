package com.peluware.springframework.crud.core.hooks;

import org.springframework.data.domain.Persistable;

/**
 * Interface for write operation hooks.
 * <p>
 * This interface provides a set of default methods that can be implemented to execute custom logic
 * during write operations such as create, update, and delete. Implementations of this interface can
 * be used to hook into the write operations without modifying the core logic.
 * </p>
 *
 * @param <E>  Entity model that extends {@link Persistable} with the specified {@code ID}.
 * @param <D>  DTO (Data Transfer Object) used for input during write operations.
 * @param <ID> The type of the entity's identifier.
 */
public interface WriteHooks<E extends Persistable<ID>, D, ID> {

    /**
     * Default implementation of the {@link WriteHooks} interface.
     * <p>
     * Provides default (empty) behavior for all the hook methods.
     * </p>
     */
    WriteHooks<?, ?, ?> DEFAULT = new WriteHooks<>() {
    };

    /**
     * Returns the default {@link WriteHooks} instance.
     * <p>
     * This static method allows access to the default (no-op) implementation of the {@link WriteHooks} interface.
     * </p>
     *
     * @param <E>  Entity model that extends {@link Persistable} with the specified {@code ID}.
     * @param <D>  DTO (Data Transfer Object) used for input during write operations.
     * @param <ID> The type of the entity's identifier.
     * @return The default {@link WriteHooks} instance.
     */
    @SuppressWarnings("unchecked")
    static <E extends Persistable<ID>, D, ID> WriteHooks<E, D, ID> getDefault() {
        return (WriteHooks<E, D, ID>) DEFAULT;
    }

    /**
     * Hook to be executed before creating an entity.
     * <p>
     * This method can be overridden to perform custom logic before creating an entity.
     * </p>
     *
     * @param dto The data transfer object (DTO) containing data for the entity.
     * @param entity The entity to be created.
     */
    default void onBeforeCreate(D dto, E entity) {
    }

    /**
     * Hook to be executed before updating an entity.
     * <p>
     * This method can be overridden to perform custom logic before updating an entity.
     * </p>
     *
     * @param dto The data transfer object (DTO) containing the updated data.
     * @param entity The entity to be updated.
     */
    default void onBeforeUpdate(D dto, E entity) {
    }

    /**
     * Hook to be executed before deleting an entity.
     * <p>
     * This method can be overridden to perform custom logic before deleting an entity.
     * </p>
     *
     * @param entity The entity to be deleted.
     */
    default void onBeforeDelete(E entity) {
    }

    /**
     * Hook to be executed after creating an entity.
     * <p>
     * This method can be overridden to perform custom logic after creating an entity.
     * </p>
     *
     * @param dto The data transfer object (DTO) used to create the entity.
     * @param entity The entity that was created.
     */
    default void onAfterCreate(D dto, E entity) {
    }

    /**
     * Hook to be executed after updating an entity.
     * <p>
     * This method can be overridden to perform custom logic after updating an entity.
     * </p>
     *
     * @param dto The data transfer object (DTO) containing the updated data.
     * @param entity The entity that was updated.
     */
    default void onAfterUpdate(D dto, E entity) {
    }

    /**
     * Hook to be executed after deleting an entity.
     * <p>
     * This method can be overridden to perform custom logic after deleting an entity.
     * </p>
     *
     * @param entity The entity that was deleted.
     */
    default void onAfterDelete(E entity) {
    }
}
