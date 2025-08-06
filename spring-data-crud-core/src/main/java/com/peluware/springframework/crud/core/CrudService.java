package com.peluware.springframework.crud.core;

import com.peluware.springframework.crud.core.hooks.CrudHooks;
import org.springframework.data.domain.Persistable;

/**
 * Represents a full CRUD service that combines reading and writing capabilities
 * for a specific entity.
 *
 * <p>This interface extends {@link Crud}, {@link ReadService}, and {@link WriteService}
 * to provide a unified API for performing create, read, update, and delete operations.</p>
 *
 * @param <E>  the entity type, which must implement {@link Persistable}
 * @param <D>  the DTO or data type used for input/output transformations
 * @param <ID> the type of the entity's identifier
 *
 * @author Luis Vasquez
 */
public non-sealed interface CrudService<E extends Persistable<ID>, D, ID> extends
        Crud,
        ReadService<E, ID>,
        WriteService<E, D, ID> {

    /**
     * Returns the CRUD hooks used for pre- and post-processing during
     * CRUD operations. By default, this returns the standard no-op hooks.
     *
     * @return the default {@link CrudHooks} instance
     */
    @Override
    default CrudHooks<E, D, ID> getHooks() {
        return CrudHooks.getDefault();
    }
}
