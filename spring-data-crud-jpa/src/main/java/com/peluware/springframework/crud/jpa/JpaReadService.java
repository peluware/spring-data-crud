package com.peluware.springframework.crud.jpa;


import cz.jirutka.rsql.parser.ast.Node;
import com.peluware.omnisearch.core.OmniSearchBaseOptions;
import com.peluware.omnisearch.core.OmniSearchOptions;
import com.peluware.omnisearch.jpa.JpaOmniSearch;
import com.peluware.springframework.crud.core.StandardReadService;
import com.peluware.springframework.crud.jpa.providers.EntityManagerProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.support.PageableExecutionUtils;

/**
 * Lightweight read-only CRUD service for JPA using {@link JpaRepository} and {@link JpaOmniSearch}.
 * <p>
 * This interface extends {@link StandardReadService} and integrates directly with the
 * OmniSearch engine to support full-text search, pagination, and structured filtering
 * via RSQL expressions, without needing to manually define {@link org.springframework.data.jpa.domain.Specification}.
 * <p>
 * It is a streamlined alternative to {@link JpaSpecificationReadService} when specification
 * composition is not required, and you're looking for a simpler plug-and-play search integration.
 * <p>
 * <b>Typical Use Case:</b> Suitable for generic read operations in admin panels, APIs, and dashboards
 * where search and filtering are needed, but with minimal customization.
 *
 * <p><b>Example:</b></p>
 * <pre>{@code
 * @Service
 * public class ProductReadService implements JpaReadService<Product, Long, ProductRepository> {
 *     // No extra implementation needed unless you want to override search options
 * }
 * }</pre>
 *
 * @param <E>  the entity type
 * @param <ID> the ID type
 * @param <R>  the repository type, must be a {@link JpaRepository}
 */

public interface JpaReadService<E extends Persistable<ID>, ID, R extends JpaRepository<E, ID>> extends
        StandardReadService<E, ID, R>,
        EntityManagerProvider {

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
        var omniSearch = new JpaOmniSearch(getEntityManager());
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
        var omniSearch = new JpaOmniSearch(getEntityManager());
        return omniSearch.count(getEntityClass(), options);
    }

    default OmniSearchOptions toSearchOptions(String search, Pageable pageable, Node query) {
        return OmniSearchOptionsFactory.create(search, pageable, query);
    }

    default OmniSearchBaseOptions toBaseSearchOptions(String search, Node query) {
        return OmniSearchOptionsFactory.create(search, query);
    }

}
