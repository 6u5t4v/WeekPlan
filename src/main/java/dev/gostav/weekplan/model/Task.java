package dev.gostav.weekplan.model;

import dev.gostav.weekplan.Time;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Task {
    private final Goal goal;
    private LocalTime startTime, endTime;

    public Task(Goal goal, LocalTime startTime, LocalTime endTime) {
        this.goal = goal;
        this.startTime = startTime;
        this.endTime = endTime.minusSeconds(endTime.getSecond());
    }

    public float getHours() {
        int durationInSeconds = (int) (startTime.until(endTime, ChronoUnit.SECONDS) + 1);
        return Time.toHours(durationInSeconds);
    }

    public Goal getGoal() {
        return goal;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String formatted() {
        return startTime + " - " + endTime + " (" + getHours() + " hours)" + "\n        " + goal.getName();
    }

    @Override
    public String toString() {
        return startTime + " - " + endTime + ": " + goal.getName() + " (" + getHours() + " hours)";
    }
}
