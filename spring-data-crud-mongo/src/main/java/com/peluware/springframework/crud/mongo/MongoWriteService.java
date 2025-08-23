package com.peluware.springframework.crud.mongo;


import com.peluware.springframework.crud.core.StandardWriteService;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Generic write service interface for Mongo-based entities.
 * <p>
 * This interface provides a contract for handling write operations
 * (such as create, update, and delete) on entities managed by a Spring Data {@link MongoRepository}.
 * It is meant to be extended or implemented in generic CRUD service layers that operate
 * over Data Transfer Objects (DTOs).
 *
 * @param <E>  the entity type, must implement {@link Persistable} with ID
 * @param <D>  the DTO type used for data transfer
 * @param <ID> the identifier type of the entity
 * @param <R>  the repository type extending {@link MongoRepository} for the entity
 */
public interface MongoWriteService<E extends Persistable<ID>, D, ID, R extends MongoRepository<E, ID>> extends StandardWriteService<E, D, ID, R> {
}