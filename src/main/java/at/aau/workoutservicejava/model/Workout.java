package at.aau.workoutservicejava.model;

import static jakarta.persistence.CascadeType.ALL;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "workouts")
public class Workout {

  public Workout() {}

  public Workout(Long userID, String workoutName, List<WorkoutSet> workoutSets) {
    this.userID = userID;
    this.workoutName = workoutName;
    this.workoutSets = workoutSets;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workout_seq_gen")
  @SequenceGenerator(
      name = "workout_seq_gen",
      sequenceName = "workout_seq",
      initialValue = 1000,
      allocationSize = 1)
  @Column(name = "workout_id", nullable = false)
  private Long workoutID;

  @Column(name = "user_id", nullable = false)
  private Long userID;

  @Column(name = "workout_name", nullable = false)
  private String workoutName;

  @Column(name = "created_date", nullable = false)
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private final LocalDateTime createdDate = LocalDateTime.now();

  @OneToMany(mappedBy = "workout", cascade = ALL, orphanRemoval = true)
  @JsonProperty("workout_sets")
  private List<WorkoutSet> workoutSets;

  public Long getWorkoutID() {
    return workoutID;
  }

  public void setWorkoutID(Long workoutID) {
    this.workoutID = workoutID;
  }

  public Long getUserID() {
    return userID;
  }

  public void setUserID(Long userID) {
    this.userID = userID;
  }

  public String getWorkoutName() {
    return workoutName;
  }

  public void setWorkoutName(String workoutName) {
    this.workoutName = workoutName;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public List<WorkoutSet> getWorkoutSets() {
    return workoutSets;
  }

  public void setWorkoutSets(List<WorkoutSet> workoutSets) {
    this.workoutSets = workoutSets;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName()
        + "("
        + "workoutId = "
        + workoutID
        + ", "
        + "userId = "
        + userID
        + ", "
        + "workoutName = "
        + workoutName
        + ", "
        + "createdAt = "
        + createdDate
        + ")";
  }
}
