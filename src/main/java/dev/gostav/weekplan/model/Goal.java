package dev.gostav.weekplan.model;

import dev.gostav.weekplan.Time;

public class Goal {
    private final String name;
    private final int minHoursPerWeek;
    private int secondsScheduled;
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

    public int getSecondsScheduled() {
        return secondsScheduled;
    }

    public void scheduleWithSeconds(int seconds) {
        this.secondsScheduled += seconds;

        if (isComplete()) {
            System.out.println("Goal " + name + " is overtime by " + Math.abs(secondsScheduled) + " seconds");
        }
    }

    public int getMaxHoursPerDay() {
        return maxHoursPerDay;
    }

    public boolean isComplete() {
        return secondsScheduled >= Time.toSeconds(minHoursPerWeek);
    }

    public float getHoursScheduled() {
        return Time.toHours(secondsScheduled);
    }
}
