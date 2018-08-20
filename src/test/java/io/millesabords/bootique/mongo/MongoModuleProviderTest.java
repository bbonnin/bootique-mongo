package io.millesabords.bootique.mongo;

import org.junit.Test;
import io.bootique.test.junit.BQModuleProviderChecker;

public class MongoModuleProviderTest {

    @Test
    public void testAutoLoadable() {
        BQModuleProviderChecker.testAutoLoadable(MongoModuleProvider.class);
    }

    @Test
    public void testMetadata() {
        BQModuleProviderChecker.testMetadata(MongoModuleProvider.class);
    }
}
