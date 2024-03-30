package dev.dab.strava.activity;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends ListCrudRepository<Activity, Long> {
    // TODO: Would like to find a nicer way of doing this
    List<Activity> findBySportTypeIgnoreCase(String sportType);

    List<Activity> findByDistanceGreaterThanEqual(Float distance);

    List<Activity> findBySportTypeIgnoreCaseAndDistanceGreaterThanEqual(String sportType, Float distance);

    Optional<Activity> findTopBySportTypeIgnoreCaseAndDistanceGreaterThanEqual(String sportType, Float distance, Sort sort);

    Optional<Activity> findTopByDistanceGreaterThanEqual(Float distance, Sort sort);

    Optional<Activity> findTopBySportTypeIgnoreCase(String sportType, Sort sort);

    Optional<Activity> findTopBy(Sort sort);
}
