package io.millesabords.bootique.mongo;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mongodb.client.MongoClient;
import io.bootique.annotation.BQConfig;
import io.bootique.config.PolymorphicConfiguration;


@BQConfig
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public abstract class ConnectionConfig implements PolymorphicConfiguration {

    protected abstract MongoClient createMongoClient();
}
