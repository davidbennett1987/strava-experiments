package dev.dab.strava.activity;

import dev.dab.strava.geo.LatLng;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ActivityController.class)
@AutoConfigureMockMvc
public class ActivityControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ActivityRepository activityRepository;

    List<Activity> activities = new ArrayList<>();

    @BeforeEach
    void setUp() {
        activities = List.of(
                new Activity(
                        1L,
                        "Test activity 1",
                        "Run",
                        OffsetDateTime.parse("2024-01-01T12:00:00Z"),
                        1080,
                        1080,
                        new LatLng("50.40216,-3.89794"),
                        new LatLng("50.40216,-3.89794"),
                        5000F,
                        null),
                new Activity(
                        2L,
                        "Test activity 2",
                        "Run",
                        OffsetDateTime.parse("2024-02-01T12:00:00Z"),
                        2050,
                        2050,
                        new LatLng("50.40216,-3.89794"),
                        new LatLng("50.40216,-3.89794"),
                        7000F,
                        null),
                new Activity(
                        3L,
                        "Test activity 3",
                        "Ride",
                        OffsetDateTime.parse("2024-03-01T12:00:00Z"),
                        5050,
                        5050,
                        new LatLng("50.40216,-3.89794"),
                        new LatLng("50.40216,-3.89794"),
                        12000F,
                        null)
        );
    }

    @Test
    void shouldFindAllActivities() throws Exception {
        String expectedResponse = """
                    [{
                        "id": 1,
                        "name": "Test activity 1",
                        "sport_type": "Run",
                        "start_date": "2024-01-01T12:00:00Z",
                        "elapsed_time": 1080,
                        "moving_time": 1080,
                        "start_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "end_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "distance": 5000.0,
                        "version": null
                    },
                    {
                        "id": 2,
                        "name": "Test activity 2",
                        "sport_type": "Run",
                        "start_date": "2024-02-01T12:00:00Z",
                        "elapsed_time": 2050,
                        "moving_time": 2050,
                        "start_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "end_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "distance": 7000.0,
                        "version": null
                    },
                    {
                        "id": 3,
                        "name": "Test activity 3",
                        "sport_type": "Ride",
                        "start_date": "2024-03-01T12:00:00Z",
                        "elapsed_time": 5050,
                        "moving_time": 5050,
                        "start_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "end_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "distance": 12000.0,
                        "version": null
                    }]
                """;

        when(activityRepository.findAll())
                .thenReturn(activities);

        mockMvc.perform(get("/api/activities"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));

    }

    @Test
    void shouldFindActivityWithValidId() throws Exception {
        String expectedResponse = """
                    {
                        "id": 1,
                        "name": "Test activity 1",
                        "sport_type": "Run",
                        "start_date": "2024-01-01T12:00:00Z",
                        "elapsed_time": 1080,
                        "moving_time": 1080,
                        "start_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "end_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "distance": 5000.0,
                        "version": null
                    }
                """;

        when(activityRepository.findById(1L))
                .thenReturn(activities.stream().filter(x -> x.id() == 1L).findFirst());

        mockMvc.perform(get("/api/activities/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldNotFindActivityWithInvalidId() throws Exception {
        when(activityRepository.findById(999999999L))
                .thenThrow(ActivityNotFoundException.class);

        mockMvc.perform(get("/api/activities/999999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldFindActivitiesBySport() throws Exception {
        String expectedResponse = """
                    [{
                        "id": 1,
                        "name": "Test activity 1",
                        "sport_type": "Run",
                        "start_date": "2024-01-01T12:00:00Z",
                        "elapsed_time": 1080,
                        "moving_time": 1080,
                        "start_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "end_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "distance": 5000.0,
                        "version": null
                    },
                    {
                        "id": 2,
                        "name": "Test activity 2",
                        "sport_type": "Run",
                        "start_date": "2024-02-01T12:00:00Z",
                        "elapsed_time": 2050,
                        "moving_time": 2050,
                        "start_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "end_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "distance": 7000.0,
                        "version": null
                    }]
                """;

        when(activityRepository.findBySportTypeIgnoreCase("run"))
                .thenReturn(
                    activities.stream().filter(x -> Objects.equals(x.sportType().toLowerCase(), "run")).toList()
                );

        mockMvc.perform(get("/api/activities?sport=run"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldFindActivitiesGteDistance() throws Exception {
        String expectedResponse = """
                    [{
                        "id": 2,
                        "name": "Test activity 2",
                        "sport_type": "Run",
                        "start_date": "2024-02-01T12:00:00Z",
                        "elapsed_time": 2050,
                        "moving_time": 2050,
                        "start_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "end_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "distance": 7000.0,
                        "version": null
                    },
                    {
                        "id": 3,
                        "name": "Test activity 3",
                        "sport_type": "Ride",
                        "start_date": "2024-03-01T12:00:00Z",
                        "elapsed_time": 5050,
                        "moving_time": 5050,
                        "start_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "end_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "distance": 12000.0,
                        "version": null
                    }]
                """;

        when(activityRepository.findByDistanceGreaterThanEqual(6000F))
                .thenReturn(
                        activities.stream().filter(x -> x.distance() >= 6000F).toList()
                );

        mockMvc.perform(get("/api/activities?distanceGte=6000"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldFindActivitiesBySportAndGteDistance() throws Exception {
        String expectedResponse = """
                    [{
                        "id": 2,
                        "name": "Test activity 2",
                        "sport_type": "Run",
                        "start_date": "2024-02-01T12:00:00Z",
                        "elapsed_time": 2050,
                        "moving_time": 2050,
                        "start_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "end_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "distance": 7000.0,
                        "version": null
                    }]
                """;

        when(activityRepository.findBySportTypeIgnoreCaseAndDistanceGreaterThanEqual("run", 6000F))
                .thenReturn(
                        activities.stream().filter(x -> Objects.equals(x.sportType().toLowerCase(), "run") && x.distance() >= 6000F).toList()
                );

        mockMvc.perform(get("/api/activities?sport=run&distanceGte=6000"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldFindBestEffortForGteDistance() throws Exception {
        String expectedResponse = """
                    {
                        "id": 2,
                        "name": "Test activity 2",
                        "sport_type": "Run",
                        "start_date": "2024-02-01T12:00:00Z",
                        "elapsed_time": 2050,
                        "moving_time": 2050,
                        "start_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "end_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "distance": 7000.0,
                        "version": null
                    }
                """;

        when(activityRepository.findTopByDistanceGreaterThanEqual(6000F, Sort.by("elapsedTime")))
                .thenReturn(
                        activities.stream().filter(x -> x.distance() >= 6000F).findFirst()
                );

        mockMvc.perform(get("/api/activities/best-effort?distanceGte=6000"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldFindBestEffortForSport() throws Exception {
        String expectedResponse = """
                    {
                        "id": 1,
                        "name": "Test activity 1",
                        "sport_type": "Run",
                        "start_date": "2024-01-01T12:00:00Z",
                        "elapsed_time": 1080,
                        "moving_time": 1080,
                        "start_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "end_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "distance": 5000.0,
                        "version": null
                    }
                """;

        when(activityRepository.findTopBySportTypeIgnoreCase("run", Sort.by("elapsedTime")))
                .thenReturn(
                        activities.stream().filter(x -> Objects.equals(x.sportType().toLowerCase(), "run")).findFirst()
                );

        mockMvc.perform(get("/api/activities/best-effort?sport=run"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldFindBestEffortBySportAndGteDistance() throws Exception {
        String expectedResponse = """
                    {
                        "id": 2,
                        "name": "Test activity 2",
                        "sport_type": "Run",
                        "start_date": "2024-02-01T12:00:00Z",
                        "elapsed_time": 2050,
                        "moving_time": 2050,
                        "start_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "end_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "distance": 7000.0,
                        "version": null
                    }
                """;

        when(activityRepository.findTopBySportTypeIgnoreCaseAndDistanceGreaterThanEqual("run", 6000F, Sort.by("elapsedTime")))
                .thenReturn(
                        activities.stream().filter(x -> Objects.equals(x.sportType().toLowerCase(), "run") && x.distance() >= 6000F).findFirst()
                );

        mockMvc.perform(get("/api/activities/best-effort?sport=run&distanceGte=6000"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldFindBestEffortFromAll() throws Exception {
        String expectedResponse = """
                    {
                        "id": 1,
                        "name": "Test activity 1",
                        "sport_type": "Run",
                        "start_date": "2024-01-01T12:00:00Z",
                        "elapsed_time": 1080,
                        "moving_time": 1080,
                        "start_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "end_latlng": {
                            "latitude": 50.40216,
                            "longitude": -3.89794
                        },
                        "distance": 5000.0,
                        "version": null
                    }
                """;

        when(activityRepository.findTopBy(Sort.by("elapsedTime")))
                .thenReturn(
                        Optional.of(activities.getFirst())
                );

        mockMvc.perform(get("/api/activities/best-effort"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }
}
