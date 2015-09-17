package org.wikimedia.json.schema.service;

import com.codahale.metrics.health.HealthCheck;

public class RoundtripHealthCheck extends HealthCheck {

    private static final String TEST = "{\"type\":\"array\",\"items\":\"string\"}";

    private final AvroResource avro;
    private final JsonResource json;

    public RoundtripHealthCheck(AvroResource avro, JsonResource json) {
        this.avro = avro;
        this.json = json;
    }

    @Override
    protected Result check() throws Exception {
        if (!avro.toJsonSchema(json.toAvroSchema(TEST)).toString().equals(TEST)) {
            return Result.unhealthy("round-trip json->avro-json failed");
        }
        return Result.healthy();
    }

}
