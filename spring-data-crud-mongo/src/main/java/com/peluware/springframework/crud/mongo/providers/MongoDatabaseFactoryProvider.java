package com.peluware.springframework.crud.mongo.providers;

import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.MongoDatabaseFactory;

public interface MongoDatabaseFactoryProvider extends MongoDatabaseProvider {

    MongoDatabaseFactory getMongoDatabaseFactory();

    @Override
    default MongoDatabase getMongoDatabase() {
        return getMongoDatabaseFactory().getMongoDatabase();
    }
}
