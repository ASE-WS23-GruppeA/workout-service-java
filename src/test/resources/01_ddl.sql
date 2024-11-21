CREATE SEQUENCE IF NOT EXISTS workout_seq START WITH 1000 INCREMENT BY 1;

CREATE TABLE workouts (
  workout_id   BIGINT PRIMARY KEY          NOT NULL,
  user_id      BIGINT                      NOT NULL,
  workout_name VARCHAR(255)                NOT NULL,
  created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS workout_set_seq START WITH 1000 INCREMENT BY 1;

CREATE TABLE workout_sets (
  workout_sets_id BIGINT PRIMARY KEY NOT NULL,
  workout_id      BIGINT             NOT NULL,
  exercise_id     BIGINT             NOT NULL,
  reps            INTEGER            NOT NULL,
  weights         DOUBLE PRECISION   NOT NULL
);

INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (100, 100, 'Leg Day', '2024-01-19T19:02:00.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (200, 100, 'Back Day', '2024-01-05T19:06:22.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (300, 100, 'Back Day', '2024-01-25T17:57:34.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (4, 100, 'Arm Day', '2024-01-07T13:38:22.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (5, 100, 'Leg Day', '2024-01-08T07:20:39.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (6, 100, 'Back Day', '2024-01-23T17:33:02.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (8, 100, 'Leg Day', '2024-01-11T13:05:26.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (9, 100, 'Shoulder Day', '2024-01-10T13:20:01.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (10, 100, 'Arm Day', '2024-01-09T02:26:56.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (11, 100, 'Leg Day', '2024-01-14T23:29:10.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (12, 100, 'Chest Day', '2024-01-18T15:54:13.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (13, 100, 'Arm Day', '2024-01-15T11:05:29.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (14, 100, 'Yoga and Stretching', '2024-01-09T05:22:30.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (15, 100, 'Leg Day', '2024-01-18T07:55:21.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (16, 100, 'Leg Day', '2024-01-08T05:54:36.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (17, 100, 'Back Day', '2024-01-22T14:21:15.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (18, 100, 'Yoga and Stretching', '2024-01-08T09:19:36.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (19, 100, 'Leg Day', '2024-01-16T02:44:44.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (20, 100, 'Back Day', '2024-01-14T00:00:46.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (21, 100, 'Leg Day', '2024-01-09T05:48:23.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (22, 100, 'Yoga and Stretching', '2024-01-04T13:37:37.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (23, 100, 'Chest Day', '2024-01-07T17:55:07.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (24, 100, 'Arm Day', '2024-01-05T07:57:22.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (25, 100, 'Shoulder Day', '2024-01-23T01:31:00.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (26, 100, 'Shoulder Day', '2024-01-14T18:51:25.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (27, 100, 'Arm Day', '2024-01-24T18:13:08.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (28, 100, 'Leg Day', '2024-01-16T20:09:40.000Z');
INSERT INTO workouts (workout_id, user_id, workout_name, created_date)
VALUES (30, 100, 'Chest Day', '2024-01-06T11:51:31.000Z');

INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (10, 100, 8, 10, 20);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (20, 200, 8, 10, 30);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (30, 300, 8, 8, 40);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (40, 4, 8, 12, 50);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (50, 5, 8, 10, 50);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (60, 6, 8, 12, 20);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (80, 8, 8, 10, 30);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (90, 9, 8, 10, 20);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (100, 10, 8, 15, 40);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (110, 11, 8, 8, 40);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (120, 12, 8, 12, 20);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (130, 13, 8, 15, 40);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (140, 14, 8, 12, 20);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (150, 15, 8, 12, 60);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (160, 16, 8, 8, 50);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (170, 17, 8, 15, 50);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (180, 18, 8, 15, 50);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (190, 19, 8, 15, 30);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (200, 20, 8, 10, 50);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (210, 21, 8, 12, 40);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (220, 22, 8, 12, 30);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (230, 23, 8, 8, 60);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (240, 24, 8, 10, 40);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (250, 25, 8, 15, 50);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (260, 26, 6, 15, 50);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (261, 26, 4, 8, 50);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (262, 26, 6, 12, 20);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (263, 26, 1, 15, 20);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (270, 27, 6, 10, 50);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (271, 27, 4, 10, 60);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (272, 27, 7, 8, 30);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (280, 28, 9, 10, 60);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (281, 28, 3, 8, 50);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (282, 28, 7, 10, 30);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (283, 28, 9, 15, 50);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (284, 28, 5, 10, 40);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (300, 30, 10, 15, 30);
INSERT INTO workout_sets (workout_sets_id, workout_id, exercise_id, reps, weights)
VALUES (301, 30, 1, 12, 40);
