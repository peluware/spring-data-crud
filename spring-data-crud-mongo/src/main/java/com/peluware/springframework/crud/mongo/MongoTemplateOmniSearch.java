package com.peluware.springframework.crud.mongo;

import com.peluware.omnisearch.core.OmniSearchBaseOptions;
import com.peluware.omnisearch.core.OmniSearchOptions;
import com.peluware.omnisearch.mongodb.MongoOmniSearch;
import com.peluware.omnisearch.mongodb.rsql.RsqlMongoBuilderOptions;
import org.bson.BsonDocument;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Objects;

public class MongoTemplateOmniSearch extends MongoOmniSearch {

    private final MongoTemplate mongoTemplate;

    public MongoTemplateOmniSearch(MongoTemplate mongoTemplate, RsqlMongoBuilderOptions rsqlBuilderOptions) {
        super(null, rsqlBuilderOptions);
        this.mongoTemplate = mongoTemplate;
    }

    public MongoTemplateOmniSearch(MongoTemplate mongoTemplate) {
        super(null);
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public <E> List<E> search(Class<E> entityClass, OmniSearchOptions options) {
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
                    .limit(pagination.size())
                    .skip(pagination.offset());
        }

        return query;
    }

    private @NotNull Query buildBaseQuery(Class<?> entityClass, OmniSearchBaseOptions options) {
        var filter = buildFilter(entityClass, options);
        return new Query() {
            @Override
            public @NotNull Document getQueryObject() {
                var bsonDoc = filter.toBsonDocument(
                        BsonDocument.class,
                        mongoTemplate.getConverter().getCodecRegistry()
                );
                return new Document(bsonDoc);
            }
        };
    }

    private static Sort buildSort(com.peluware.domain.Sort sort) {
        return Sort.by(sort.orders().stream().map(o -> o.ascending() ?
                Sort.Order.asc(o.property()) :
                Sort.Order.desc(o.property())
        ).toList());
    }
}
