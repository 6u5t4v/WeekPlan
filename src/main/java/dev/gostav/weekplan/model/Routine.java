package dev.gostav.weekplan.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Routine {
    private String name;
    private LocalTime startTime;
    private List<Task> tasks = new ArrayList<>();

    public Routine(String routineName, LocalTime startTime) {
        this.name = routineName;
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
