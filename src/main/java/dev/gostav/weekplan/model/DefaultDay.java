package dev.gostav.weekplan.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DefaultDay {
    private String name;
    private String description;
    private int breaks;
    private int breakDurationInMinutes = 60;
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

        LocalTime dayStart = routines.get(0).getStartTime();
        LocalTime dayEnd = routines.get(routines.size() - 1).getStartTime();

        int dayDuration = dayEnd.toSecondOfDay() - dayStart.toSecondOfDay();

        if (dayEnd.isBefore(dayStart)) {
            dayDuration = 86400 - dayStart.toSecondOfDay() + dayEnd.toSecondOfDay();
//            System.out.println("Routine " + name + " starts before the first routine");
        }

        int breakTime = breaks * breakDurationInMinutes * 60;
        int freeTime = dayDuration - occupiedTime - breakTime;

        if (!isFullDay(dayDuration, freeTime, occupiedTime, breakTime)) {
            throw new RuntimeException("Day is not full: " + name + " " + freeTime + " " + occupiedTime + " " + breakTime);
        }

        return freeTime;
    }

    private boolean isFullDay(int dayDuration, int freeTime, int occupiedTime, int breakDuration) {
        return (freeTime + occupiedTime + breakDuration) == dayDuration;
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
