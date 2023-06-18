package dev.gostav.weekplan;

public class Time {
    public static float toHours(int seconds) {
        float hours = (float) seconds / 3600;
        return toHoursFormatted(hours);
    }

    public static int toSeconds(float hours) {
        return (int) (hours * 3600);
    }

    public static float toHoursFormatted(float hoursScheduled) {
        return Math.round(hoursScheduled * 2) / 2f;
    }
}
