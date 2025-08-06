package com.peluware.springframework.crud.jpa;


import jakarta.persistence.EntityManager;
import lombok.Getter;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/**
 * Abstract write service implementation for JPA without requiring a predefined {@link org.springframework.data.jpa.repository.JpaRepository}.
 * <p>
 * This service internally uses a {@link SimpleJpaRepository} constructed with the given entity class
 * and {@link EntityManager} to provide standard write operations.
 * </p>
 *
 * @param <E>  the entity type, extending {@link Persistable}
 * @param <D>  the Data Transfer Object (DTO) type
 * @param <ID> the type of the entity identifier
 */
@Getter
public abstract class SimpleJpaWriteService<E extends Persistable<ID>, D, ID> implements
        JpaWriteService<E, D, ID, SimpleJpaRepository<E, ID>> {

    protected final Class<E> entityClass;
    protected final EntityManager entityManager;
    protected final SimpleJpaRepository<E, ID> repository;

    /**
     * Constructs the write service with the specified entity class and entity manager.
     *
     * @param entityClass   the JPA entity class
     * @param entityManager the JPA entity manager used for persistence operations
     */
    protected SimpleJpaWriteService(Class<E> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
        this.repository = new SimpleJpaRepository<>(entityClass, entityManager);
    }
}
