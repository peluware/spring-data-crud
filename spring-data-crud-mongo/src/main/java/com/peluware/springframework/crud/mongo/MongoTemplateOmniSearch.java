package com.peluware.springframework.crud.mongo;

import com.peluware.domain.Order;
import com.peluware.omnisearch.OmniSearch;
import com.peluware.omnisearch.OmniSearchBaseOptions;
import com.peluware.omnisearch.OmniSearchOptions;
import com.peluware.omnisearch.mongodb.DefaultMongoOmniSearchFilterBuilder;
import com.peluware.omnisearch.mongodb.MongoOmniSearchFilterBuilder;
import org.bson.BsonDocument;
import org.bson.Document;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Objects;

public class MongoTemplateOmniSearch implements OmniSearch {

    private final MongoTemplate mongoTemplate;
    private final MongoOmniSearchFilterBuilder filterBuilder;

    public MongoTemplateOmniSearch(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.filterBuilder = new DefaultMongoOmniSearchFilterBuilder();
    }

    @Override
    public <E> List<E> list(Class<E> entityClass, OmniSearchOptions options) {
        Objects.requireNonNull(entityClass, "Entity class cannot be null");
        Objects.requireNonNull(options, "Options cannot be null");

        var query = buildQuery(entityClass, options);
        return mongoTemplate.find(query, entityClass);
    }

    @Override
    public <E> long count(Class<E> entityClass, OmniSearchBaseOptions options) {
        Objects.requireNonNull(entityClass, "Entity class cannot be null");
        Objects.requireNonNull(options, "Options cannot be null");

        var query = buildBaseQuery(entityClass, options);
        return mongoTemplate.count(query, entityClass);
    }

    public <E> Query buildQuery(Class<E> entityClass, OmniSearchOptions options) {
        final var query = buildBaseQuery(entityClass, options);

        var sort = options.getSort();
        if (sort.isSorted()) {
            query.with(buildSort(sort));
        }

        var pagination = options.getPagination();
        if (pagination.isPaginated()) {
            query
                    .limit(pagination.getSize())
                    .skip(pagination.getOffset());
        }

        return query;
    }

    private @NonNull Query buildBaseQuery(Class<?> entityClass, OmniSearchBaseOptions options) {
        var filter = filterBuilder.buildFilter(entityClass, options);
        return new Query() {
            @Override
            public @NonNull Document getQueryObject() {
                var bsonDoc = filter.toBsonDocument(
                        BsonDocument.class,
                        mongoTemplate.getConverter().getCodecRegistry()
                );
                return new Document(bsonDoc);
            }
        };
    }

    private static Sort buildSort(com.peluware.domain.Sort sort) {
        return Sort.by(sort.orders().stream().map(o -> o.direction() == Order.Direction.ASC ?
                Sort.Order.asc(o.property()) :
                Sort.Order.desc(o.property())
        ).toList());
    }
}
