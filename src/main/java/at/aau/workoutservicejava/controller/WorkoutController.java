package at.aau.workoutservicejava.controller;

import at.aau.workoutservicejava.dto.WorkoutDto;
import at.aau.workoutservicejava.model.Workout;
import at.aau.workoutservicejava.service.WorkoutService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

  private static final Logger log = LoggerFactory.getLogger(WorkoutController.class);

  private final WorkoutService workoutService;

  public WorkoutController(WorkoutService workoutService) {
    this.workoutService = workoutService;
  }

  @GetMapping
  public ResponseEntity<List<Workout>> getAllWorkouts() {
    return ResponseEntity.ok(workoutService.getAllWorkouts());
  }

  @PostMapping("/create")
  public ResponseEntity<Workout> createWorkout(@RequestBody WorkoutDto workoutDto) {
    try {
      Workout workout = workoutService.createWorkout(workoutDto);

      return ResponseEntity.ok(workout);
    } catch (Exception e) {
      log.error("Error creating workout; return 500 - [workoutDto='{}']", workoutDto, e);

      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/{workoutId}")
  public ResponseEntity<Workout> getWorkoutById(@PathVariable Long workoutId) {
    return workoutService
        .getWorkoutById(workoutId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Workout>> getAllWorkoutsByUserId(@PathVariable Long userId) {
    try {
      List<Workout> workouts = workoutService.getAllWorkoutsByUserId(userId);

      return ResponseEntity.ok(workouts);
    } catch (Exception e) {
      log.error("Error getting workouts by user id; return 500 - [userId='{}']", userId, e);

      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/last/{userId}")
  public ResponseEntity<Workout> getLastWorkoutByUserId(@PathVariable Long userId) {
    return workoutService
        .getLastWorkoutByUserId(userId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/user/{userID}/{workoutName}")
  public ResponseEntity<Workout> getWorkoutByUserIdAndByWorkoutName(
      @PathVariable Long userID, @PathVariable String workoutName) {
    return workoutService
        .getWorkoutByUserIdAndWorkoutName(userID, workoutName)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{workoutId}")
  public ResponseEntity<Void> deleteWorkoutById(@PathVariable Long workoutId) {
    try {
      workoutService.deleteWorkoutById(workoutId);

      return ResponseEntity.ok().build();
    } catch (Exception e) {
      log.error("Error deleting workout; return 500 - [workoutId='{}']", workoutId, e);

      return ResponseEntity.internalServerError().build();
    }
  }
}
