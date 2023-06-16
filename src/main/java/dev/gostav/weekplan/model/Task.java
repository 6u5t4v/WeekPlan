package dev.gostav.weekplan.model;

import java.time.LocalTime;

public class Task {
    private final Goal goal;
    private final LocalTime startTime;

    public Task(Goal goal, LocalTime startTime) {
        this.goal = goal;
        this.startTime = startTime;
    }

    public String getName() {
        return goal.getName();
    }

    public Goal getGoal() {
        return goal;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public int getDurationInSeconds() {
        return (int) (goal.getHoursFullFilled() * 3600);
    }
}
