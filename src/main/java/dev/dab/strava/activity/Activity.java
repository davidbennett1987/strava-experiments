package dev.dab.strava.activity;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.dab.strava.geo.LatLng;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table(name = "activity")
public record Activity (
    @Id
    @JsonProperty("id")
    Long id,

    @JsonProperty("name")
    String name,

    @JsonProperty("sport_type")
    String sportType,

    @JsonProperty("start_date")
    OffsetDateTime startDate,

    @JsonProperty("elapsed_time")
    Integer elapsedTime,

    @JsonProperty("moving_time")
    Integer movingTime,

    @Embedded.Nullable(prefix = "start_")
    @JsonProperty("start_latlng")
    LatLng startLatLng,

    @Embedded.Nullable(prefix = "end_")
    @JsonProperty("end_latlng")
    LatLng endLatLng,

    @JsonProperty("distance")
    Float distance,

    @Version
    Integer version
) {
}