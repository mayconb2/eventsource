package com.ambev.eventsource.consumer.config;

import com.mongodb.client.MongoClient;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Value("${eventStore.mongodb.database}")
    private String eventStoreDataBase;

    @Value("${eventStore.mongodb.collection}")
    private String eventStoreDataBaseCollection;

    // The `MongoEventStorageEngine` stores each event in a separate MongoDB document
    @Bean
    public EventStorageEngine storageEngine(MongoClient mongoClient) {
        return MongoEventStorageEngine
                .builder()
                .mongoTemplate(DefaultMongoTemplate
                        .builder()
                        .mongoDatabase(mongoClient, eventStoreDataBase)
                        .domainEventsCollectionName(eventStoreDataBaseCollection)
                        .build())
                .build();
    }

}