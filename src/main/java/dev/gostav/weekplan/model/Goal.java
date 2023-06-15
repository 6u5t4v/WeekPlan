package dev.gostav.weekplan.model;

public class Goal extends Task {
    private String description;
    private int hoursPrWeek;
    private float hoursFullFilled;
    private boolean daily;

    public Goal(String title, String description, int hoursPrWeek, boolean daily) {
        super(title);
        this.description = description;
        this.hoursPrWeek = hoursPrWeek;
        this.daily = daily;
    }

    @Override
    public int getDurationInSeconds() {
        return super.getDurationInSeconds();
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
