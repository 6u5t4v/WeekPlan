package dev.gostav.weekplan.model;

public class Task {
    private String title;

    public Task(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getDurationInSeconds() {
        return 0;
    }
}
