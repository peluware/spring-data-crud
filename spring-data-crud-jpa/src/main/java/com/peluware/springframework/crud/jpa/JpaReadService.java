package com.peluware.springframework.crud.jpa;


import com.peluware.omnisearch.jpa.JpaOmniSearch;
import com.peluware.springframework.crud.core.StandardReadService;
import com.peluware.springframework.crud.jpa.providers.EntityManagerProvider;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Generic read-only service interface for JPA-based entities.
 * <p>
 *     This interface provides a contract for handling read operations
 *     (such as find and list) on entities managed by a Spring Data {@link JpaRepository}.
 *  </p>
 * @param <E> the entity type, must implement {@link Persistable} with identifier of type {@code ID}
 * @param <ID> the type of the entity identifier
 * @param <R> the repository type that extends {@link JpaRepository}
 */
public interface JpaReadService<E extends Persistable<ID>, ID, R extends JpaRepository<E, ID>> extends
        StandardReadService<E, ID, R>,
        EntityManagerProvider {

    @Override
    default JpaOmniSearch getOmniSearch() {
        return new JpaOmniSearch(getEntityManager());
    }

}
