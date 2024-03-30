package dev.dab.strava.activity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class ActivityDataLoader implements CommandLineRunner {
    private final static Logger log = LoggerFactory.getLogger(ActivityDataLoader.class);
    private final ObjectMapper objectMapper;
    private final ActivityRepository activityRepository;

    public ActivityDataLoader(ObjectMapper objectMapper, ActivityRepository activityRepository) {
        this.objectMapper = objectMapper;
        this.activityRepository = activityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (activityRepository.count() == 0) {
            String ACTIVITIES_JSON = "/data/activities.json";

            log.info("Loading activities into database from file: {}", ACTIVITIES_JSON);

            try (InputStream inputStream = TypeReference.class.getResourceAsStream((ACTIVITIES_JSON))) {
                ActivityList response = objectMapper.readValue(inputStream, ActivityList.class);

                log.info("Found {} activities in data file", response.activities().size());

                List<Activity> saved = activityRepository.saveAll(response.activities());

                log.info("Saved {} activities", saved.size());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read data", e);
            }
        }
    }
}
