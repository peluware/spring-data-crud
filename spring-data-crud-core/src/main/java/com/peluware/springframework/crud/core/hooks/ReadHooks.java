package com.peluware.springframework.crud.core.hooks;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Persistable;

/**
 * Interface for read operation hooks.
 * <p>
 * This interface provides a set of default methods that can be implemented to execute custom logic
 * during read operations such as finding, counting, checking existence, or pagination of entities.
 * Implementations of this interface can be used to hook into the read operations without modifying the core logic.
 * </p>
 *
 * @param <E>  Entity model that extends {@link Persistable} with the specified {@code ID}.
 * @param <ID> The type of the entity's identifier.
 */
public interface ReadHooks<E extends Persistable<ID>, ID> {

    /**
     * Default implementation of the {@link ReadHooks} interface.
     * <p>
     * Provides default (empty) behavior for all the hook methods.
     * </p>
     */
    ReadHooks<?, ?> DEFAULT = new ReadHooks<>() {
    };

    /**
     * Returns the default {@link ReadHooks} instance.
     * <p>
     * This static method allows access to the default (no-op) implementation of the {@link ReadHooks} interface.
     * </p>
     *
     * @param <E>  Entity model that extends {@link Persistable} with the specified {@code ID}.
     * @param <ID> The type of the entity's identifier.
     * @return The default {@link ReadHooks} instance.
     */
    @SuppressWarnings("unchecked")
    static <E extends Persistable<ID>, ID> ReadHooks<E, ID> getDefault() {
        return (ReadHooks<E, ID>) DEFAULT;
    }

    /**
     * Hook to be executed after finding a single entity.
     * <p>
     * This method can be overridden to perform custom logic after an entity is found.
     * </p>
     *
     * @param entity The entity that was found.
     */
    default void onFind(E entity) {
    }

    /**
     * Hook to be executed after finding a collection of entities by their IDs.
     * <p>
     * This method can be overridden to perform custom logic after a collection of entities is found.
     * </p>
     *
     * @param entities The collection of entities that were found.
     * @param ids The collection of IDs for the found entities.
     */
    default void onFind(Iterable<E> entities, Iterable<ID> ids) {
    }

    /**
     * Hook to be executed after counting the number of entities.
     * <p>
     * This method can be overridden to perform custom logic after counting the entities.
     * </p>
     *
     * @param count The count of entities.
     */
    default void onCount(long count) {
    }

    /**
     * Hook to be executed after checking the existence of an entity.
     * <p>
     * This method can be overridden to perform custom logic after checking if an entity exists.
     * </p>
     *
     * @param exists {@code true} if the entity exists, {@code false} otherwise.
     * @param id The ID of the entity.
     */
    default void onExists(boolean exists, ID id) {
    }

    /**
     * Hook to be executed after paginating a collection of entities.
     * <p>
     * This method can be overridden to perform custom logic after a page of entities is retrieved.
     * </p>
     *
     * @param page The page of entities.
     */
    default void onPage(Page<E> page) {
    }
}
