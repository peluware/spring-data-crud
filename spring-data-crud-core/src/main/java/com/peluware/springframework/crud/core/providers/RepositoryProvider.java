package com.peluware.springframework.crud.core.providers;

/**
 * Provides access to a repository instance.
 *
 * <p>This interface is typically implemented to supply the repository
 * used by CRUD services for data access operations.</p>
 *
 * @param <R> the repository type
 */
public interface RepositoryProvider<R> {

    /**
     * Returns the repository instance.
     *
     * @return the repository
     */
    R getRepository();
}
