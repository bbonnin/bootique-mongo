package io.millesabords.bootique.mongo;

import com.google.inject.Module;
import io.bootique.BQModule;
import io.bootique.BQModuleProvider;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;


public class MongoModuleProvider implements BQModuleProvider {

    @Override
    public Module module() {
        return new MongoModule();
    }

    @Override
    public Map<String, Type> configs() {
        return Collections.singletonMap("mongo", MongoClientFactory.class);
    }

    @Override
    public BQModule.Builder moduleBuilder() {
        return BQModuleProvider.super
                .moduleBuilder()
                .description("Provides integration with MongoDB client library.");
    }
}
