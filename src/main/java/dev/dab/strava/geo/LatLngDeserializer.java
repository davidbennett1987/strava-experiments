package dev.dab.strava.geo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class LatLngDeserializer extends JsonDeserializer {
    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.readValueAsTree();

        if (nodeisValidObject(node)) {
            return new LatLng(node.get("latitude").asDouble(), node.get("longitude").asDouble());
        }

        if (nodeIsValidArray(node)) {
            return new LatLng(node.get(0).asDouble(), node.get(1).asDouble());
        }

        throw new LatLngDeserializationException("Invalid LatLng provided");
    }

    private boolean nodeIsValidArray(JsonNode node) {
        if (node == null || !node.isArray()) {
            return false;
        }

        if (node.get(0) == null && node.get(1) == null) {
            return false;
        }

        return node.get(0).isDouble() && node.get(1).isDouble();
    }

    private boolean nodeisValidObject(JsonNode node) {
        if (node == null || !node.isObject()) {
            return false;
        }

        if (node.get("latitude") == null && node.get("longitude") == null) {
            return false;
        }

        return node.get("latitude").isDouble() && node.get("longitude").isDouble();
    }
}
