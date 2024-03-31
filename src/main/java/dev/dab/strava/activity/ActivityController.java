package dev.dab.strava.activity;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activities")
class ActivityController {
    private final ActivityRepository activityRepository;

    ActivityController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @GetMapping
    List<Activity> findAll(@RequestParam Optional<String> sport, @RequestParam Optional<Float> distanceGte) {
        Boolean isSportQuery = !(sport.orElse("").isEmpty());
        Boolean isDistanceQuery = !(distanceGte.orElse(0F) == 0F);

        // TODO: Clean this up, would like to replace the else ifs with some kind of query interface...
        if (isSportQuery && isDistanceQuery) {
            return activityRepository.findBySportTypeIgnoreCaseAndDistanceGreaterThanEqual(sport.get(), distanceGte.get());
        } else if (isDistanceQuery) {
            return activityRepository.findByDistanceGreaterThanEqual(distanceGte.get());
        } else if (isSportQuery) {
            return activityRepository.findBySportTypeIgnoreCase(sport.get());
        }

        return activityRepository.findAll();
    }

    @GetMapping("/best-effort")
    Optional<Activity> bestEffort(@RequestParam Optional<String> sport, @RequestParam Optional<Float> distanceGte) {
        Boolean isSportQuery = !(sport.orElse("").isEmpty());
        Boolean isDistanceQuery = !(distanceGte.orElse(0F) == 0F);

        // TODO: Clean this up, would like to replace the else ifs with some kind of query interface...
        if (isSportQuery && isDistanceQuery) {
            return Optional.ofNullable(activityRepository.findTopBySportTypeIgnoreCaseAndDistanceGreaterThanEqual(sport.get(), distanceGte.get(), Sort.by("elapsedTime"))
                    .orElseThrow(ActivityNotFoundException::new));
        } else if (isDistanceQuery) {
            return Optional.ofNullable(activityRepository.findTopByDistanceGreaterThanEqual(distanceGte.get(), Sort.by("elapsedTime"))
                    .orElseThrow(ActivityNotFoundException::new));
        } else if (isSportQuery) {
            return Optional.ofNullable(activityRepository.findTopBySportTypeIgnoreCase(sport.get(), Sort.by("elapsedTime"))
                    .orElseThrow(ActivityNotFoundException::new));
        }

        return Optional.ofNullable(activityRepository.findTopBy(Sort.by("elapsedTime"))
                .orElseThrow(ActivityNotFoundException::new));
    }

    @GetMapping("/{id}")
    Optional<Activity> findById(@PathVariable Long id) {
        return Optional.ofNullable(activityRepository.findById(id)
                .orElseThrow(ActivityNotFoundException::new));
    }
}
