package io.millesabords.bootique.mongo;

import com.google.inject.Inject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import io.bootique.BQCoreModule;
import io.bootique.BQRuntime;
import io.bootique.Bootique;
import org.bson.BSON;
import org.bson.Document;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MongoIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoIntegrationTest.class);

    @ClassRule
    public static GenericContainer mongo = new GenericContainer("mongo:4.1")
            .withExposedPorts(27017);

    @Test
    public void test() throws IOException, TimeoutException {
        LOGGER.info("MongoDB: {}", mongo.getContainerIpAddress() + ":" + mongo.getMappedPort(27017));

        BQRuntime runtime = Bootique
                .app()
                .args("--config=classpath:mongo.yml")
                .module((binder) -> {
                    binder.bind(Tester.class);
                })
                .autoLoadModules()
                .createRuntime();

        runtime.getInstance(Tester.class).test();
    }
}


class Tester {

    private MongoClientFactory clientFactory;

    @Inject
    public Tester(MongoClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    public void test() {
        MongoCollection testColl1 = getCollection("localMongo1", "testdb", "test");
        testColl1.insertOne(new Document("hello", "world"));

        MongoCollection testColl2 = getCollection("localMongo2", "testdb", "test");
        assertEquals(1, testColl2.countDocuments());

        DeleteResult result = testColl2.deleteMany(new Document("hello", "world"));
        assertEquals(1, result.getDeletedCount());
    }

    private MongoCollection getCollection(String connectionName, String dbName, String collName) {
        MongoClient mongo = clientFactory.get(connectionName);
        MongoDatabase db = mongo.getDatabase(dbName);
        MongoCollection coll = db.getCollection(collName);

        return coll;
    }
}