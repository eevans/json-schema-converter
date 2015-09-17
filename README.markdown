json-schema-converter
=====================

Convert from [jsonschema](http://json-schema.org/) to [avro](http://avro.apache.org)
schema, and vice versa.

Build
-----
    $ mvn package

Run
---
    $ java -jar target/json-schema-converter-<version>-jar-with-dependencies.jar server config.yaml


Try
---
    $ curl -D - -X POST -H "Content-Type: application/json" -d @map.json http://localhost:8080/json; echo
    HTTP/1.1 200 OK
    Date: Thu, 17 Sep 2015 21:55:36 GMT
    Content-Type: application/json
    Content-Length: 30

    {"type":"map","values":"long"}
    $ curl -D - -X POST -H "Content-Type: application/json" -d @map.avro http://localhost:8080/avro; echo
    HTTP/1.1 200 OK
    Date: Thu, 17 Sep 2015 21:56:37 GMT
    Content-Type: application/json
    Content-Length: 120

    {"type":"object","additionalProperties":{"type":"integer","minimum":-9223372036854775808,"maximum":9223372036854775807}}