package com.peluware.springframework.crud.mongo.providers;

import org.springframework.data.mongodb.core.MongoTemplate;

public interface MongoTemplateProvider {

    MongoTemplate getMongoTemplate();
}
