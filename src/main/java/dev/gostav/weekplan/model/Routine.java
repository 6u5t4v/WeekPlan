package dev.gostav.weekplan.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Routine {
    private String name;
    private LocalTime startTime, endTime;
    private List<Task> tasks = new ArrayList<>();

    public Routine(String routineName, LocalTime startTime, LocalTime endTime) {
        this.name = routineName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getDurationInSeconds() {
        return endTime.toSecondOfDay() - startTime.toSecondOfDay();
    }

    public String getName() {
        return name;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
