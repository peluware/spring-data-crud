package com.peluware.springframework.crud.jpa;

import com.peluware.springframework.crud.core.CrudOperation;
import org.springframework.data.jpa.domain.Specification;

/**
 * Interface for combining JPA {@link Specification} instances with additional criteria.
 * <p>
 * Implementations can override {@link #combineSpecification(Specification, CrudOperation)}
 * to add extra filtering logic depending on the CRUD operation context.
 * </p>
 *
 * @param <E> the entity type for the specification
 */
public interface SpecificationCombiner<E> {

    /**
     * Combines the given {@link Specification} with additional criteria based on the operation.
     *
     * @param spec      the original specification to combine
     * @param operation the current CRUD operation context
     * @return the combined specification, by default returns the original specification unchanged
     */
    default Specification<E> combineSpecification(Specification<E> spec, CrudOperation operation) {
        return spec;
    }
}
