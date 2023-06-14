package dev.gostav.weekplan.model.layouts;

import dev.gostav.weekplan.model.Routine;
import dev.gostav.weekplan.model.Task;

import java.util.ArrayList;
import java.util.List;

public class DefaultDay {
    private String name;
    private String description;
    private int breaks;
    private List<Routine> routines = new ArrayList<>();

    public DefaultDay(String name, String description, int breaks) {
        this.name = name;
        this.description = description;
        this.breaks = breaks;
    }

    public int freeTimeInSeconds() {
        int occupiedTime = 0;

        for (Routine routine : routines) {
            int duration = routine.getDurationInSeconds();
            occupiedTime += duration;
        }
        return 86400 - occupiedTime;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getBreaks() {
        return breaks;
    }

    public List<Routine> getRoutines() {
        return routines;
    }

    public void setRoutines(List<Routine> routines) {
        this.routines = routines;
    }
}
