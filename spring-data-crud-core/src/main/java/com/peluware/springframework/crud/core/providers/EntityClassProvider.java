package com.peluware.springframework.crud.core.providers;

/**
 * Provides the entity class type.
 *
 * <p>This interface is typically used to supply the {@link Class} object
 * representing the entity type {@code E} in generic CRUD services or repositories.</p>
 *
 * @param <E> the entity type
 */
public interface EntityClassProvider<E> {

    /**
     * Returns the {@link Class} object corresponding to the entity type.
     *
     * @return the entity class
     */
    Class<E> getEntityClass();
}
