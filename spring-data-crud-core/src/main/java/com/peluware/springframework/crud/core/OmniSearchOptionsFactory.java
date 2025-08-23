package com.peluware.springframework.crud.core;

import com.peluware.domain.Order;
import com.peluware.domain.Pagination;
import com.peluware.domain.Sort;
import cz.jirutka.rsql.parser.ast.Node;
import com.peluware.omnisearch.core.OmniSearchBaseOptions;
import com.peluware.omnisearch.core.OmniSearchOptions;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Pageable;

/**
 * Utility class for creating {@link OmniSearchOptions} and {@link OmniSearchBaseOptions}
 * from Spring Data {@link Pageable} and RSQL query nodes.
 */
@UtilityClass
public class OmniSearchOptionsFactory {

    /**
     * Creates an {@link OmniSearchOptions} instance based on search text, pagination, sorting, and RSQL query.
     *
     * @param search   the search string, may be null or blank
     * @param pageable the Spring Data pageable object containing pagination and sorting information
     * @param query    the parsed RSQL query node, may be null
     * @return a configured {@link OmniSearchOptions} instance
     */
    public static OmniSearchOptions create(String search, Pageable pageable, Node query) {
        var sort = pageable.getSort();
        return new OmniSearchOptions()
                .search(search)
                .query(query)
                .pagination(pageable.isUnpaged()
                        ? Pagination.unpaginated()
                        : new Pagination(pageable.getPageNumber(), pageable.getPageSize()))
                .sort(sort.isUnsorted()
                        ? Sort.unsorted()
                        : new Sort(sort.stream()
                        .map(order -> new Order(order.getProperty(), order.isAscending()))
                        .toList()));
    }

    /**
     * Creates an {@link OmniSearchBaseOptions} instance based on search text and an RSQL query.
     *
     * @param search the search string, may be null or blank
     * @param query  the parsed RSQL query node, may be null
     * @return a configured {@link OmniSearchBaseOptions} instance
     */
    public static OmniSearchBaseOptions create(String search, Node query) {
        return new OmniSearchBaseOptions()
                .search(search)
                .query(query);
    }
}
