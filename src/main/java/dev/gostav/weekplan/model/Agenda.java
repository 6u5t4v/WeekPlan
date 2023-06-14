package dev.gostav.weekplan.model;

import dev.gostav.weekplan.model.layouts.DefaultDay;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private final DefaultDay defaultDay;
    private final List<Task> tasks;

    public Agenda(DefaultDay defaultDay, List<Task> tasks) {
        this.defaultDay = defaultDay;
        this.tasks = tasks;
    }

    public DefaultDay getDefaultDay() {
        return defaultDay;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
}
