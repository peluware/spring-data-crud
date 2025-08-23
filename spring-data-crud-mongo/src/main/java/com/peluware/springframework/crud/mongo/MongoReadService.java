package com.peluware.springframework.crud.mongo;


import com.peluware.omnisearch.core.OmniSearch;
import com.peluware.omnisearch.mongodb.MongoOmniSearch;
import com.peluware.springframework.crud.core.StandardReadService;
import com.peluware.springframework.crud.mongo.providers.MongoDatabaseProvider;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * Generic read-only service interface for Mongo-based entities.
 * <p>
 *     This interface provides a contract for handling read operations
 *     (such as find and list) on entities managed by a Spring Data {@link MongoRepository}.
 *  </p>
 * @param <E> the entity type, must implement {@link Persistable} with identifier of type {@code ID}
 * @param <ID> the type of the entity identifier
 * @param <R> the repository type that extends {@link MongoRepository}
 */
public interface MongoReadService<E extends Persistable<ID>, ID, R extends MongoRepository<E, ID>> extends
        StandardReadService<E, ID, R>,
        MongoDatabaseProvider {

    @Override
    default MongoOmniSearch getOmniSearch() {
        return new MongoOmniSearch(getMongoDatabase());
    }
}
