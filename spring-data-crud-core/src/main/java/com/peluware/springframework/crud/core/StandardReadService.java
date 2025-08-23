package com.peluware.springframework.crud.core;

import com.peluware.omnisearch.core.OmniSearch;
import com.peluware.omnisearch.core.OmniSearchBaseOptions;
import com.peluware.omnisearch.core.OmniSearchOptions;
import com.peluware.springframework.crud.core.exceptions.NotFoundEntityException;
import com.peluware.springframework.crud.core.providers.EntityClassProvider;
import com.peluware.springframework.crud.core.providers.RepositoryProvider;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

/**
 * Standard read-only service implementation for CRUD operations,
 * leveraging Spring Data repositories.
 * <p>
 * This interface provides default implementations of read operations
 * delegating to a repository that combines {@link ListCrudRepository}
 * and {@link PagingAndSortingRepository}.
 * </p>
 *
 * @param <E>  the entity type extending {@link Persistable} with identifier of type {@code ID}
 * @param <ID> the type of the entity identifier
 * @param <R>  the repository type that extends both {@link ListCrudRepository} and {@link PagingAndSortingRepository}
 */
public interface StandardReadService<
        E extends Persistable<ID>,
        ID,
        R extends ListCrudRepository<E, ID> & PagingAndSortingRepository<E, ID>>
        extends ReadService<E, ID>, EntityClassProvider<E>, RepositoryProvider<R> {

    /**
     * {@inheritDoc}
     */
    @Override
    default Page<E> internalPage(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default E internalFind(ID id) {
        return getRepository()
                .findById(id)
                .orElseThrow(() -> new NotFoundEntityException(getEntityClass(), id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default List<E> internalFind(List<ID> ids) {
        return getRepository().findAllById(ids);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default long internalCount() {
        return getRepository().count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default boolean internalExists(ID id) {
        return getRepository().existsById(id);
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
        var entityClass = getEntityClass();
        var omniSearch = getOmniSearch();
        return PageableExecutionUtils.getPage(
                omniSearch.search(entityClass, options),
                pageable,
                () -> omniSearch.count(entityClass, options)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default long internalCount(String search) {
        return internalCount(search, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default long internalCount(String search, Node query) {
        var options = toBaseSearchOptions(search, query);
        var omniSearch = getOmniSearch();
        return omniSearch.count(getEntityClass(), options);
    }

    OmniSearch getOmniSearch();

    default OmniSearchOptions toSearchOptions(String search, Pageable pageable, Node query) {
        return OmniSearchOptionsFactory.create(search, pageable, query);
    }

    default OmniSearchBaseOptions toBaseSearchOptions(String search, Node query) {
        return OmniSearchOptionsFactory.create(search, query);
    }
}
