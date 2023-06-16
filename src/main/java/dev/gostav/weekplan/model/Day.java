package dev.gostav.weekplan.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Day {
    private final LocalDate date;
    private final DefaultDay defaultDay;
    private final List<Task> tasks;

    public Day(LocalDate date, DefaultDay defaultDay) {
        this.date = date;
        this.defaultDay = defaultDay;
        this.tasks = new ArrayList<>();
    }

    public static List<Day[]> splitDaysIntoWeeks(Day[] days) {
        List<Day[]> weeklyDays = new ArrayList<>();

        int weekNumber = 0;
        int weekDay = 0;
        Day[] week = new Day[7];

        for (Day day : days) {
            week[weekDay] = day;
            weekDay++;

            if (weekDay == 7) {
                weeklyDays.add(week);
                week = new Day[7];
                weekDay = 0;
                weekNumber++;
            }
        }

        return weeklyDays;
    }

    public DefaultDay getDefaultDay() {
        return defaultDay;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public int getFreeTimeInSeconds() {
        int freeTime = 86400;

        for (Task task : tasks) {
            freeTime -= task.getDurationInSeconds();
        }

        return freeTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTaskForHour(int hour) {
        String agenda = "";
//        for (Task task : tasks) {
//            if (task.getHour() == hour) {
//                agenda += task.getTitle() + "\n";
//            }
//        }
        return agenda;
    }
}
