package dev.gostav.weekplan.model;

import java.time.LocalTime;

public class DayPlan {
    private String name;
    private String description;
    private LocalTime morning, night;
    private LocalTime[] routines;

    public DayPlan(String name, String description, LocalTime morning, LocalTime night, LocalTime[] routines) {
        this.name = name;
        this.description = description;
        this.morning = morning;
        this.night = night;
        this.routines = routines;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getMorning() {
        return morning;
    }

    public LocalTime getNight() {
        return night;
    }

    public LocalTime[] getRoutines() {
        return routines;
    }
}
