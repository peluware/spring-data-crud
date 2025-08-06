package com.peluware.springframework.crud.jpa;


import com.peluware.springframework.crud.core.exceptions.NotFoundEntityException;
import com.peluware.springframework.crud.core.CrudService;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * JPA-based CRUD service with support for queries using {@link org.springframework.data.jpa.domain.Specification}.
 * Combines standard read and write operations for persistent entities.
 *
 * @param <E>  Entity type, must implement {@link org.springframework.data.domain.Persistable}
 * @param <D>  DTO type associated with the entity
 * @param <ID> Type of the entity identifier
 * @param <R>  JPA repository extending {@link JpaRepository} and {@link JpaSpecificationExecutor}
 */
public interface JpaSpecificationCrudService<E extends Persistable<ID>, D, ID, R extends JpaRepository<E, ID> & JpaSpecificationExecutor<E>> extends
        JpaWriteService<E, D, ID, R>,
        JpaSpecificationReadService<E, ID, R>,
        CrudService<E, D, ID> {

    /**
     * {@inheritDoc}
     */
    @Override
    default E internalFind(ID id) throws NotFoundEntityException {
        return JpaSpecificationReadService.super.internalFind(id);
    }
}
