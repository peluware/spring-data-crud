package com.peluware.springframework.crud.jpa;


import com.peluware.springframework.crud.core.StandardCrudService;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Generic CRUD service interface for JPA-based entities.
 * <p>
 * Combines read and write operations for entities managed by Spring Data JPA repositories.
 *
 * @param <E>  the entity type, which must implement {@link org.springframework.data.domain.Persistable}
 * @param <D>  the DTO (Data Transfer Object) type
 * @param <ID> the identifier type of the entity
 * @param <R>  the Spring Data JPA repository type for the entity
 */
public interface JpaCrudService<E extends Persistable<ID>, D, ID, R extends JpaRepository<E, ID>> extends
        JpaReadService<E, ID, R>,
        JpaWriteService<E, D, ID, R>,
        StandardCrudService<E, D, ID, R> {
}