
# Bootique MongoDB client
Provides MongoDB integration with [Bootique](http://bootique.io)

> Example [here](https://github.com/bbonnin/bootique-mongo-example)

## Configuration

2 ways:
* by using uri:
```yaml
mongo:
  connections:
    localMongo:
      type: 'uri'
      uri: mongodb://username:pwd@localhost:27017/?authSource=admin
```

* by using detailed informations:
```yaml
mongo:
  connections:
    localMongo:
      type: 'server'
      host: localhost
      port: 27017
      user: username
      password: pwd
      source: admin
```

