package at.aau.workoutservicejava.repository;

import at.aau.workoutservicejava.model.Workout;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
  List<Workout> findAllByUserID(Long userID);

  Optional<Workout> findFirstByUserIDOrderByCreatedDateDesc(Long userId);

  Optional<Workout> findFirstByUserIDAndWorkoutName(Long userId, String workoutName);
}
