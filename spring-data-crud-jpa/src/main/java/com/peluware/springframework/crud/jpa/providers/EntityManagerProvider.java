package com.peluware.springframework.crud.jpa.providers;

import jakarta.persistence.EntityManager;

/**
 * Provides access to a JPA {@link EntityManager}.
 * <p>
 * Classes implementing this interface expose a method to obtain the
 * {@link EntityManager} instance used for database operations.
 * </p>
 */
public interface EntityManagerProvider {

    /**
     * Returns the {@link EntityManager} instance.
     *
     * @return the entity manager
     */
    EntityManager getEntityManager();
}
