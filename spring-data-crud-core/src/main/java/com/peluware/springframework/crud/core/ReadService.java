package com.peluware.springframework.crud.core;

import cz.jirutka.rsql.parser.ast.Node;
import com.peluware.springframework.crud.core.exceptions.NotFoundEntityException;
import com.peluware.springframework.crud.core.hooks.ReadHooks;
import com.peluware.springframework.crud.core.utils.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Defines a generic read-only service for CRUD operations.
 * <p>
 * Provides default implementations for common read operations such as pagination,
 * finding entities by ID, checking existence, and counting records.
 * Also allows hook interception via {@link ReadHooks} for event-based extensions.
 *
 * @param <E>  the entity type, must implement {@link Persistable}
 * @param <ID> the ID type of the entity
 */
@Validated
public non-sealed interface ReadService<E extends Persistable<ID>, ID> extends Crud {

    /**
     * Returns the hooks associated with this read service. Can be overridden to customize hook behavior.
     *
     * @return the read hooks for this service
     */
    default ReadHooks<E, ID> getHooks() {
        return ReadHooks.getDefault();
    }

    /**
     * Retrieves a paginated list of entities based on the given search text, query node, and pagination information.
     *
     * @param search   optional search text to filter results
     * @param pageable pagination configuration
     * @param query    optional parsed RSQL query node
     * @return a page of entities matching the criteria
     */
    default Page<E> page(String search, Pageable pageable, Node query) {
        Crud.preProccess(this, CrudOperation.PAGE);

        var normalized = StringUtils.normalize(search);
        var page = resolvePage(normalized, pageable, query);
        var hooks = getHooks();

        hooks.onPage(page);
        return page;
    }

    /**
     * Retrieves a single entity by its ID.
     *
     * @param id the ID of the entity to retrieve
     * @return the entity if found
     * @throws NotFoundEntityException if the entity does not exist
     */
    default E find(ID id) {
        Crud.preProccess(this, CrudOperation.FIND);

        var model = internalFind(id);
        var hooks = getHooks();

        hooks.onFind(model);
        return model;
    }

    /**
     * Retrieves a list of entities by their IDs.
     *
     * @param ids the list of IDs to look up
     * @return a list of entities found
     */
    default List<E> find(List<ID> ids) {
        Crud.preProccess(this, CrudOperation.FIND);

        var list = internalFind(ids);
        var hooks = getHooks();

        hooks.onFind(list, ids);
        return list;
    }

    /**
     * Counts the number of entities that match the given search and query criteria.
     *
     * @param search optional search string
     * @param query  optional RSQL query node
     * @return the total count of matching entities
     */
    default long count(String search, Node query) {
        Crud.preProccess(this, CrudOperation.COUNT);

        var count = resolveCount(search, query);
        var hooks = getHooks();

        hooks.onCount(count);
        return count;
    }

    /**
     * Checks whether an entity with the given ID exists.
     *
     * @param id the ID to check for existence
     * @return true if the entity exists, false otherwise
     */
    default boolean exists(ID id) {
        Crud.preProccess(this, CrudOperation.EXISTS);

        var exists = internalExists(id);
        var hooks = getHooks();

        hooks.onExists(exists, id);
        return exists;
    }

    // ----- Abstract/internal methods (must be implemented by concrete service) -----

    /**
     * Retrieves a simple paginated list of all entities.
     *
     * @param pageable pagination information
     * @return a page of entities
     */
    Page<E> internalPage(Pageable pageable);

    /**
     * Retrieves a paginated list of entities matching the given search text.
     *
     * @param search   the search string
     * @param pageable pagination configuration
     * @return a page of matching entities
     */
    Page<E> internalSearch(String search, Pageable pageable);

    /**
     * Retrieves a paginated list of entities based on search and query filters.
     *
     * @param search   the search string
     * @param pageable pagination configuration
     * @param query    the parsed RSQL query
     * @return a page of matching entities
     */
    Page<E> internalSearch(String search, Pageable pageable, Node query);

    /**
     * Retrieves an entity by its ID, or throws if not found.
     *
     * @param id the ID of the entity
     * @return the found entity
     * @throws NotFoundEntityException if not found
     */
    E internalFind(ID id) throws NotFoundEntityException;

    /**
     * Retrieves a list of entities matching the given list of IDs.
     *
     * @param ids the list of IDs
     * @return the corresponding entities
     */
    List<E> internalFind(List<ID> ids);

    /**
     * Returns the total number of entities.
     *
     * @return total count
     */
    long internalCount();

    /**
     * Returns the number of entities matching the search term.
     *
     * @param search the search string
     * @return count of matching entities
     */
    long internalCount(String search);

    /**
     * Returns the number of entities matching the search term and query.
     *
     * @param search the search string
     * @param query  the RSQL query node
     * @return count of matching entities
     */
    long internalCount(String search, Node query);

    /**
     * Checks whether an entity with the given ID exists.
     *
     * @param id the ID to check
     * @return true if exists, false otherwise
     */
    boolean internalExists(ID id);

    // ----- Internal resolution methods (used in default logic) -----

    /**
     * Resolves the correct page method to call depending on the presence of search and query.
     *
     * @param search   normalized search string
     * @param pageable pagination configuration
     * @param query    optional RSQL query node
     * @return a page of entities
     */
    private Page<E> resolvePage(String search, Pageable pageable, Node query) {
        if (query == null) {
            if (search == null || search.isBlank()) {
                return internalPage(pageable);
            }
            return internalSearch(search, pageable);
        } else {
            return internalSearch(search, pageable, query);
        }
    }

    /**
     * Resolves the correct count method to call based on the presence of search and query.
     *
     * @param search the search string
     * @param query  the RSQL query node
     * @return number of matching entities
     */
    private long resolveCount(String search, Node query) {
        if (query == null) {
            if (search == null || search.isBlank()) {
                return internalCount();
            }
            return internalCount(search);
        } else {
            return internalCount(search, query);
        }
    }
}
