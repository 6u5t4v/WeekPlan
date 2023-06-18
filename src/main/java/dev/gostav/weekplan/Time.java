package dev.gostav.weekplan;

public class Time {
    public static float toHours(int seconds) {
        float hours = (float) seconds / 3600;
        return Math.round(hours * 2) / 2f;
    }

    public static int toSeconds(float hours) {
        return (int) (hours * 3600);
    }
}
