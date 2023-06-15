package dev.gostav.weekplan.model;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private final DefaultDay defaultDay;
    private final List<Task> tasks;

    public Agenda(DefaultDay defaultDay) {
        this.defaultDay = defaultDay;
        this.tasks = new ArrayList<>();
    }

    public DefaultDay getDefaultDay() {
        return defaultDay;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public void addTask(Goal goal) {
        tasks.add(goal);
    }
}
