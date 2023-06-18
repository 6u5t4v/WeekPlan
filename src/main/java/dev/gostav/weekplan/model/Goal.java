package dev.gostav.weekplan.model;

import dev.gostav.weekplan.Time;

public class Goal {
    private final String name;
    private final int minHoursPerWeek;
    private float hoursScheduled;
    private final boolean isDailyGoal;
    private final int maxHoursPerDay;

    public Goal(String name, int minHoursPerWeek, boolean isDailyGoal, int maxHoursPerDay) {
        this.name = name;
        this.minHoursPerWeek = minHoursPerWeek;
        this.isDailyGoal = isDailyGoal;
        this.maxHoursPerDay = maxHoursPerDay;
    }

    public String getName() {
        return name;
    }

    public int getMinHoursPerWeek() {
        return minHoursPerWeek;
    }

    public boolean isDailyGoal() {
        return isDailyGoal;
    }

    public void addHours(float hours) {
        this.hoursScheduled += hours;
    }

    public int getMaxHoursPerDay() {
        return maxHoursPerDay;
    }

    public boolean isComplete() {
        return hoursScheduled >= minHoursPerWeek;
    }

    public float getHoursScheduled() {
        return Time.toHoursFormatted(hoursScheduled);
    }
}
