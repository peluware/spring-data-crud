package com.peluware.springframework.crud.jpa;


import jakarta.persistence.EntityManager;
import lombok.Getter;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/**
 * Abstract read-only service implementation for JPA without requiring a predefined {@link org.springframework.data.jpa.repository.JpaRepository}.
 * <p>
 * This service uses a {@link SimpleJpaRepository} internally, constructed from the given entity class
 * and {@link EntityManager}, to provide standard read operations.
 * </p>
 *
 * @param <E>  the entity type, extending {@link Persistable}
 * @param <ID> the type of the entity identifier
 */
@Getter
public abstract class SimpleJpaReadService<E extends Persistable<ID>, ID> implements JpaReadService<E, ID, SimpleJpaRepository<E, ID>> {

    protected final Class<E> entityClass;
    protected final EntityManager entityManager;
    protected final SimpleJpaRepository<E, ID> repository;

    /**
     * Constructs the read service with the specified entity class and entity manager.
     *
     * @param entityClass   the JPA entity class
     * @param entityManager the JPA entity manager used for queries
     */
    protected SimpleJpaReadService(Class<E> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
        this.repository = new SimpleJpaRepository<>(entityClass, entityManager);
    }
}
