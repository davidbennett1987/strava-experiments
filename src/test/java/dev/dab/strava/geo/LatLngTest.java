package dev.dab.strava.geo;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LatLngTest {
    @Test
    void createValidLatLngFromString() {
        String latLngString = "50.40216,-3.89794";

        LatLng latLng = new LatLng(latLngString);

        assertEquals(latLng.getLatitude(), 50.40216);
        assertEquals(latLng.getLongitude(), -3.89794);
    }

    @Test
    void expectExceptionFromLatLngWithMissingCommaFromString() {
        String invalidLatLngString = "50.40216-3.89794";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            LatLng latLng = new LatLng(invalidLatLngString);
        });

        String expectedMessage = "Invalid latLng";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void expectExceptionFromLatLngWithInvalidDoubleFromString() {
        String invalidLatLngString = "50.40216,x";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            LatLng latLng = new LatLng(invalidLatLngString);
        });

        String expectedMessage = "Invalid latLng";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void deserializeFromJsonObject() throws JsonProcessingException {
        String latLngJson = """
                {
                    "latitude": 50.40216,
                    "longitude": -3.89794
                }
            """;

        LatLng deserialized = new ObjectMapper().readValue(latLngJson, LatLng.class);

        assertEquals(50.40216, deserialized.getLatitude());
        assertEquals(-3.89794, deserialized.getLongitude());
    }

    @Test
    void expectExceptionWhenDeserializeFromInvalidJsonObject() {
        String latLngJson = """
                    "latitude": 50.40216,
                    "longitude": -3.89794
                }
            """;

        Exception exception = assertThrows(Exception.class, () -> {
            LatLng deserialized = new ObjectMapper().readValue(latLngJson, LatLng.class);
        });
    }

    @Test
    void expectExceptionWhenDeserializeWithInvalidDoubleJsonObject() {
        String latLngJson = """
                {
                    "latitude": 50.40216,
                    "longitude": "x"
                }
            """;

        Exception exception = assertThrows(LatLngDeserializationException.class, () -> {
            LatLng deserialized = new ObjectMapper().readValue(latLngJson, LatLng.class);
        });
    }

    @Test
    void deserializeFromJsonArray() throws JsonProcessingException {
        String latLngJson = """
                [
                    50.40216,
                    -3.89794
                ]
            """;

        LatLng deserialized = new ObjectMapper().readValue(latLngJson, LatLng.class);

        assertEquals(50.40216, deserialized.getLatitude());
        assertEquals(-3.89794, deserialized.getLongitude());
    }

    @Test
    void expectExceptionWhenDeserializeFromInvalidJsonArray() {
        String latLngJson = """
                    50.40216,
                    -3.89794
                ]
            """;

        Exception exception = assertThrows(Exception.class, () -> {
            LatLng deserialized = new ObjectMapper().readValue(latLngJson, LatLng.class);
        });
    }

    @Test
    void expectExceptionWhenDeserializeFromInvalidDoubleJsonArray() {
        String latLngJson = """
                [
                    50.40216,
                    "x"
                ]
            """;

        Exception exception = assertThrows(LatLngDeserializationException.class, () -> {
            LatLng deserialized = new ObjectMapper().readValue(latLngJson, LatLng.class);
        });
    }
}
