package io.millesabords.bootique.mongo;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.bootique.annotation.BQConfig;
import io.bootique.annotation.BQConfigProperty;

import java.util.Arrays;


@BQConfig
@JsonTypeName("server")
public class ServerConnectionConfig extends ConnectionConfig {

    private String host;
    private int port = 27017;
    private String user;
    private char[] password;
    private String source;

    @Override
    protected MongoClient createMongoClient() {
        MongoClientSettings.Builder settingsBuilder = MongoClientSettings.builder()
                .applyToClusterSettings(builder ->
                        builder.hosts(Arrays.asList(new ServerAddress(host, port))));

        setCredentialIfNeeded(settingsBuilder);

        MongoClient mongoClient = MongoClients.create(settingsBuilder.build());

        return mongoClient;
    }

    private void setCredentialIfNeeded(MongoClientSettings.Builder settingsBuilder) {
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(user)) {
            MongoCredential credential = MongoCredential.createCredential(user, source, password);
            settingsBuilder.credential(credential);
        }
    }

    @BQConfigProperty
    public void setHost(String host) {
        this.host = host;
    }

    @BQConfigProperty
    public void setPort(int port) {
        this.port = port;
    }

    @BQConfigProperty
    public void setUser(String user) {
        this.user = user;
    }

    @BQConfigProperty
    public void setPassword(String password) {
        this.password = password.toCharArray();
    }

    @BQConfigProperty
    public void setSource(String source) {
        this.source = source;
    }
}

