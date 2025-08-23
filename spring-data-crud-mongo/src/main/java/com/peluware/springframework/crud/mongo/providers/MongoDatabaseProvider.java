package com.peluware.springframework.crud.mongo.providers;

import com.mongodb.client.MongoDatabase;

/**
 * Provides access to a MongoDatabase instance.
 */
public interface MongoDatabaseProvider {

    /**
     * Gets the MongoDatabase instance.
     * @return the MongoDatabase instance
     */
    MongoDatabase getMongoDatabase();
}
