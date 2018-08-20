package io.millesabords.bootique.mongo;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.bootique.ConfigModule;
import io.bootique.config.ConfigurationFactory;


public class MongoModule extends ConfigModule {

    @Provides
    @Singleton
    MongoClientFactory provideRabbitMQFactory(ConfigurationFactory configurationFactory) {
        return configurationFactory.config(MongoClientFactory.class, configPrefix);
    }
}
