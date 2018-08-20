package io.millesabords.bootique.mongo;

import com.mongodb.client.MongoClient;
import io.bootique.annotation.BQConfig;
import io.bootique.annotation.BQConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;


@BQConfig
public class MongoClientFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoClientFactory.class);

    private Map<String, ConnectionConfig> connections;
    private Map<String, MongoClient> mongoClients = new HashMap<>();
    private volatile boolean shutdown;

    public void shutdown() {

        LOGGER.info("Shutting down MongoDB connections...");

        this.shutdown = true;

        mongoClients.values()
                .stream()
                .forEach(c -> c.close());
    }

    public synchronized MongoClient get(String connectionName) {

        MongoClient client = mongoClients.get(connectionName);

        if (client == null) {
            client = createConnection(connectionName);
            mongoClients.put(connectionName, client);
        }

        return client;
    }

    private MongoClient createConnection(String connectionName) {

        if (shutdown) {
            throw new IllegalStateException("Shutdown on going...");
        }

        return connections
                .computeIfAbsent(connectionName, name -> {
                    throw new IllegalStateException("No configuration for '" + name + "'");
                })
                .createMongoClient();
    }

    @BQConfigProperty
    public void setConnections(Map<String, ConnectionConfig> connections) {
        this.connections = connections;
    }
}
