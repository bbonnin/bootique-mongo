package io.millesabords.bootique.mongo;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.bootique.annotation.BQConfig;
import io.bootique.annotation.BQConfigProperty;


/**
 * Example: "mongodb://user1:pwd1@host1/?authSource=db1"
 */
@BQConfig
@JsonTypeName("uri")
public class UriConnectionConfig extends ConnectionConfig {

    private String uri;

    @Override
    protected MongoClient createMongoClient() {
        MongoClient mongoClient = MongoClients.create(uri);
        return mongoClient;
    }

    @BQConfigProperty
    public void setUri(String uri) {
        this.uri = uri;
    }
}
