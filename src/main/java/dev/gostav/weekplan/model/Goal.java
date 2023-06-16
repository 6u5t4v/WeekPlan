package dev.gostav.weekplan.model;

public class Goal {
    private String name, description;
    private int hoursPrWeek;
    private float hoursFullFilled;
    private boolean daily;

    public Goal(String name, String description, int hoursPrWeek, boolean daily) {
        this.name = name;
        this.description = description;
        this.hoursPrWeek = hoursPrWeek;
        this.daily = daily;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getHoursPrWeek() {
        return hoursPrWeek;
    }

    public boolean isDaily() {
        return daily;
    }

    public float getHoursFullFilled() {
        return hoursFullFilled;
    }

    public void setHoursFullFilled(float hours) {
        this.hoursFullFilled = hours;
    }
}
