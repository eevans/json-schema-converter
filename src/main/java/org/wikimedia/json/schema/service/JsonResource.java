package org.wikimedia.json.schema.service;

import java.io.IOException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.avro.Schema;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.DevNullProcessingReport;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.core.tree.CanonicalSchemaTree;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.util.ValueHolder;
import com.github.fge.jsonschema2avro.AvroWriterProcessor;

@Path("/json")
@Produces(MediaType.APPLICATION_JSON)
public class JsonResource {

    private final AvroWriterProcessor processor = new AvroWriterProcessor();
    private final ProcessingReport report = new DevNullProcessingReport();

    @POST
    @Timed
    public String toAvroSchema(String jsonSchema) throws IOException, ProcessingException {
        JsonNode json = JsonLoader.fromString(jsonSchema);
        final SchemaTree tree = new CanonicalSchemaTree(json);
        final ValueHolder<SchemaTree> input = ValueHolder.hold("schema", tree);
        final Schema avroSchema = processor.process(report, input).getValue();
        return avroSchema.toString(false);
    }

}
