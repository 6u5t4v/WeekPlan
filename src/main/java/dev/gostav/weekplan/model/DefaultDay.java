package dev.gostav.weekplan.model;

import java.util.ArrayList;
import java.util.List;

public class DefaultDay {
    private String name;
    private String description;
    private List<Routine> routines = new ArrayList<>();

    public DefaultDay(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int freeTimeInSeconds() {
//        int occupiedTime = 0;
//
//        for (Routine routine : routines) {
//            int duration = routine.getDurationInSeconds();
//            occupiedTime += duration;
//        }
//
//        LocalTime dayStart = routines.get(0).getStartTime();
//        LocalTime dayEnd = routines.get(routines.size() - 1).getStartTime();
//
//        int dayDuration = dayEnd.toSecondOfDay() - dayStart.toSecondOfDay();
//
//        if (dayEnd.isBefore(dayStart)) {
//            dayDuration = 86400 - dayStart.toSecondOfDay() + dayEnd.toSecondOfDay();
//        }
//
//        int freeTime = dayDuration - occupiedTime;
//
//        if (!isFullDay(dayDuration, freeTime, occupiedTime)) {
//            throw new RuntimeException("Day is not full: " + name + " " + freeTime + " " + occupiedTime);
//        }

        return 0;
    }

    private boolean isFullDay(int dayDuration, int freeTime, int occupiedTime) {
        return (freeTime + occupiedTime) == dayDuration;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Routine> getRoutines() {
        return new ArrayList<>(routines);
    }

    public void setRoutines(List<Routine> routines) {
        this.routines = routines;
    }
}
