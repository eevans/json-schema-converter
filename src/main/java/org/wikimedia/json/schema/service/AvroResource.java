package org.wikimedia.json.schema.service;

import java.io.IOException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.avro.Avro2JsonSchemaProcessor;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.DevNullProcessingReport;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.core.tree.JsonTree;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.tree.SimpleJsonTree;
import com.github.fge.jsonschema.core.util.ValueHolder;

@Path("/avro")
@Produces(MediaType.APPLICATION_JSON)
public class AvroResource {

    private final Avro2JsonSchemaProcessor processor = new Avro2JsonSchemaProcessor();
    private final ProcessingReport report = new DevNullProcessingReport();

    @POST
    @Timed
    public JsonNode toJsonSchema(String avroSchema) throws IOException, ProcessingException {
        JsonNode avro = JsonLoader.fromString(avroSchema);
        ValueHolder<JsonTree> input = ValueHolder.<JsonTree> hold(new SimpleJsonTree(avro));
        ValueHolder<SchemaTree> jsonSchema = processor.process(report, input);
        return jsonSchema.getValue().getBaseNode();
    }

}
