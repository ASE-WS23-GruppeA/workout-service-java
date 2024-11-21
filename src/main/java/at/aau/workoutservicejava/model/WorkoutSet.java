package at.aau.workoutservicejava.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import org.springframework.lang.Nullable;

@Entity(name = "workout_sets")
public class WorkoutSet {

  public WorkoutSet() {}

  public WorkoutSet(Long exerciseID, Integer reps, Double weights, Workout workout) {
    this.exerciseID = exerciseID;
    this.reps = reps;
    this.weights = weights;
    this.workout = workout;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workout_set_seq_gen")
  @SequenceGenerator(
      name = "workout_set_seq_gen",
      sequenceName = "workout_set_seq",
      initialValue = 1000,
      allocationSize = 1)
  @Column(name = "workout_sets_id", nullable = false)
  private Long workoutSetsID;

  @Column(name = "exercise_id", nullable = false)
  private Long exerciseID;

  @Column(name = "reps", nullable = false)
  private Integer reps;

  @Column(name = "weights", nullable = false)
  private Double weights;

  @ManyToOne(optional = false)
  @JoinColumn(name = "workout_id", nullable = false, updatable = false)
  @JsonIgnore
  private Workout workout;

  public Long getWorkoutSetsID() {
    return workoutSetsID;
  }

  public void setWorkoutSetsID(Long workoutSetsID) {
    this.workoutSetsID = workoutSetsID;
  }

  public Long getExerciseID() {
    return exerciseID;
  }

  public void setExerciseID(Long exerciseID) {
    this.exerciseID = exerciseID;
  }

  public Integer getReps() {
    return reps;
  }

  public void setReps(Integer reps) {
    this.reps = reps;
  }

  public Double getWeights() {
    return weights;
  }

  public void setWeights(Double weights) {
    this.weights = weights;
  }

  @JsonIgnore
  public Workout getWorkout() {
    return workout;
  }

  public void setWorkout(Workout workout) {
    this.workout = workout;
  }

  @Nullable
  public Long getWorkoutID() {
    return workout != null ? workout.getWorkoutID() : null;
  }

  @Override
  public String toString() {
    return "WorkoutSet{"
        + "workoutSetsID="
        + workoutSetsID
        + ", exerciseID="
        + exerciseID
        + ", repetitions="
        + reps
        + ", weight="
        + weights
        + '}';
  }
}
