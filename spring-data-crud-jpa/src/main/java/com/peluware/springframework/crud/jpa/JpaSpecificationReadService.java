package com.peluware.springframework.crud.jpa;


import cz.jirutka.rsql.parser.ast.Node;
import com.peluware.omnisearch.core.OmniSearchBaseOptions;
import com.peluware.omnisearch.core.OmniSearchOptions;
import com.peluware.omnisearch.jpa.JpaOmniSearchPredicateBuilder;
import com.peluware.springframework.crud.core.CrudOperation;
import com.peluware.springframework.crud.core.providers.EntityClassProvider;
import com.peluware.springframework.crud.core.providers.RepositoryProvider;
import com.peluware.springframework.crud.core.exceptions.NotFoundEntityException;
import com.peluware.springframework.crud.core.ReadService;
import com.peluware.springframework.crud.jpa.providers.EntityManagerProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Generic read service for JPA entities using {@link Specification} and dynamic filtering.
 * <p>
 * This interface provides a complete, extensible implementation of {@link ReadService}
 * for Spring Data JPA, leveraging {@link JpaSpecificationExecutor} to support powerful dynamic
 * queries using RSQL, search strings, and runtime predicates.
 * <p>
 * Designed for use cases where access to entities should be filtered or constrained — for example,
 * verifying that the currently authenticated user is associated with or owns the entity.
 * It supports specification composition through {@link SpecificationCombiner}, making it
 * easy to add authorization, soft deletes, or tenant-based filters.
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * @Service
 * public class OrderReadService implements JpaSpecificationReadService<Order, UUID, OrderRepository> {
 *
 *     @Override
 *     public Specification<Order> combineSpecification(Specification<Order> original, CrudOperation op) {
 *         return original.and((root, query, cb) -> cb.equal(root.get("ownerId"), getCurrentUserId()));
 *     }
 * }
 * }</pre>
 *
 * @param <E>  the entity type, must extend {@link Persistable}
 * @param <ID> the ID type of the entity
 * @param <R>  the Spring Data JPA repository, must implement {@link JpaSpecificationExecutor}
 */

public interface JpaSpecificationReadService<E extends Persistable<ID>, ID, R extends JpaSpecificationExecutor<E>> extends
        ReadService<E, ID>,
        RepositoryProvider<R>,
        EntityManagerProvider,
        EntityClassProvider<E>,
        SpecificationCombiner<E> {

    /**
     * {@inheritDoc}
     */
    @Override
    default Page<E> internalPage(Pageable pageable) {
        Specification<E> spec = (root, query, cb) -> null;
        return getRepository().findAll(combineSpecification(spec, CrudOperation.PAGE), pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Page<E> internalSearch(String search, Pageable pageable) {
        return internalSearch(search, pageable, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Page<E> internalSearch(String search, Pageable pageable, Node query) {
        var options = toSearchOptions(search, pageable, query);
        Specification<E> spec = (root, q, cb) -> JpaOmniSearchPredicateBuilder.buildPredicate(
                getEntityManager(),
                root,
                options
        );
        return getRepository().findAll(combineSpecification(spec, CrudOperation.PAGE), pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default E internalFind(ID id) {
        Specification<E> spec = (root, query, cb) -> cb.equal(root.get(getIdFieldName()), id);
        return getRepository().findOne(combineSpecification(spec, CrudOperation.FIND)).orElseThrow(() -> new NotFoundEntityException(getEntityClass(), id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default List<E> internalFind(List<ID> ids) {
        Specification<E> spec = (root, query, cb) -> root.get(getIdFieldName()).in(ids);
        return getRepository().findAll(combineSpecification(spec, CrudOperation.FIND));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default long internalCount() {
        Specification<E> spec = (root, query, cb) -> null;
        return getRepository().count(combineSpecification(spec, CrudOperation.COUNT));
    }


    /**
     * {@inheritDoc}
     */
    default long internalCount(String search) {
        return internalCount(search, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default long internalCount(String search, Node query) {
        var options = toBaseSearchOptions(search, query);
        Specification<E> spec = (root, q, cb) -> JpaOmniSearchPredicateBuilder.buildPredicate(
                getEntityManager(),
                root,
                options
        );
        return getRepository().count(combineSpecification(spec, CrudOperation.COUNT));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default boolean internalExists(ID id) {
        Specification<E> spec = (root, query, cb) -> cb.equal(root.get(getIdFieldName()), id);
        return getRepository().exists(combineSpecification(spec, CrudOperation.EXISTS));
    }

    default String getIdFieldName() {
        return "id";
    }

    default OmniSearchOptions toSearchOptions(String search, Pageable pageable, Node query) {
        return OmniSearchOptionsFactory.create(search, pageable, query);
    }

    default OmniSearchBaseOptions toBaseSearchOptions(String search, Node query) {
        return OmniSearchOptionsFactory.create(search, query);
    }
}
