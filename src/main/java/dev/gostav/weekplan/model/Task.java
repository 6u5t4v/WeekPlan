package dev.gostav.weekplan.model;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Task {
    private final Goal goal;
    private LocalTime startTime, endTime;

    public Task(Goal goal, LocalTime startTime, LocalTime endTime) {
        this.goal = goal;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public float getHours() {
        // If endTime is before startTime, then the task is scheduled to the next day
        if (startTime.getHour() > endTime.getHour()) {
            return startTime.until(endTime, ChronoUnit.HOURS) + 1;
        }

        return endTime.getHour() - startTime.getHour();
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
}
