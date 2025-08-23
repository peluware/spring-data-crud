package com.peluware.springframework.crud.mongo.providers;

import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;

public interface MongoTemplateProvider extends MongoDatabaseProvider {

    MongoTemplate getMongoTemplate();

    @Override
    default MongoDatabase getMongoDatabase() {
        return getMongoTemplate().getDb();
    }
}
