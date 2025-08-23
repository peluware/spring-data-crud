package com.peluware.springframework.crud.mongo;


import com.peluware.springframework.crud.core.StandardCrudService;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Generic CRUD service interface for Mongo-based entities.
 * <p>
 * Combines read and write operations for entities managed by Spring Data Mongo repositories.
 *
 * @param <E>  the entity type, which must implement {@link Persistable}
 * @param <D>  the DTO (Data Transfer Object) type
 * @param <ID> the identifier type of the entity
 * @param <R>  the Spring Data JPA repository type for the entity
 */
public interface MongoCrudService<E extends Persistable<ID>, D, ID, R extends MongoRepository<E, ID>> extends
        MongoReadService<E, ID, R>,
        MongoWriteService<E, D, ID, R>,
        StandardCrudService<E, D, ID, R> {
}