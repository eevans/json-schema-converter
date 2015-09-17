package org.wikimedia.json.schema.service;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class SchemaService extends Application<Configuration> {

    @Override
    public void run(Configuration config, Environment env) throws Exception {
        AvroResource avroResource = new AvroResource();
        JsonResource jsonResource = new JsonResource();
        env.healthChecks().register("round-trip", new RoundtripHealthCheck(avroResource, jsonResource));
        env.jersey().register(avroResource);
        env.jersey().register(jsonResource);
    }

    public static void main(String... args) throws Exception {
        new SchemaService().run(args);
    }

}
