package dev.gostav.weekplan.model;

public class Goal {
    private final String name;
    private int minHoursPerWeek, secondsRemaning;
    private final boolean isDailyGoal;

    public Goal(String name, int minHoursPerWeek, boolean isDailyGoal) {
        this.name = name;
        this.minHoursPerWeek = minHoursPerWeek;
        this.secondsRemaning = minHoursPerWeek * 60 * 60;
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

    public int getSecondsRemaining() {
        return secondsRemaning;
    }

    public void subtractSeconds(int seconds) {
        this.secondsRemaning -= seconds;

        if (isComplete()) {
            System.out.println("Goal " + name + " is complete");
        }
    }

    public boolean isComplete() {
        return secondsRemaning <= 0;
    }
}
