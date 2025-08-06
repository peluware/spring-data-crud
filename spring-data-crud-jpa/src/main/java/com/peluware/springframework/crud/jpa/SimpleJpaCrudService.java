package com.peluware.springframework.crud.jpa;


import jakarta.persistence.EntityManager;
import lombok.Getter;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/**
 * Abstract CRUD service implementation for JPA without requiring a predefined {@link org.springframework.data.jpa.repository.JpaRepository}.
 * <p>
 * Provides basic CRUD operations by internally managing a {@link SimpleJpaRepository}
 * constructed with the given entity class and {@link EntityManager}.
 * </p>
 *
 * @param <E>  the entity type, extending {@link Persistable}
 * @param <D>  the DTO type associated with the entity
 * @param <ID> the type of the entity identifier
 */
@Getter
public abstract class SimpleJpaCrudService<E extends Persistable<ID>, D, ID> implements JpaCrudService<E, D, ID, SimpleJpaRepository<E, ID>> {

    protected final Class<E> entityClass;
    protected final EntityManager entityManager;
    protected final SimpleJpaRepository<E, ID> repository;

    /**
     * Constructs the service with the given entity class and entity manager.
     *
     * @param entityClass   the JPA entity class
     * @param entityManager the JPA entity manager used for persistence operations
     */
    protected SimpleJpaCrudService(Class<E> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
        this.repository = new SimpleJpaRepository<>(entityClass, entityManager);
    }
}
