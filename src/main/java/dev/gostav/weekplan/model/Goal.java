package dev.gostav.weekplan.model;

public class Goal {
    private final String name;
    private final int minHoursPerWeek;
    private int secondsScheduled;
    private final boolean isDailyGoal;

    public Goal(String name, int minHoursPerWeek, boolean isDailyGoal) {
        this.name = name;
        this.minHoursPerWeek = minHoursPerWeek;
        this.isDailyGoal = isDailyGoal;
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

    public boolean isComplete() {
        return secondsScheduled > minHoursPerWeek * 60 * 60;
    }

    public float getHoursScheduled() {
        return secondsScheduled / 3600f;
    }
}
