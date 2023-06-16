package dev.gostav.weekplan.model;

import java.time.LocalDate;
import java.util.List;

public class Schedule {
    private LocalDate from;
    private LocalDate to;

    private Day[] days;

    public Schedule(LocalDate startDate, LocalDate to, Day[] dailyDays) {
        this.from = startDate;
        this.to = to;
        this.days = dailyDays;
    }

    public int getFreeTimeInSeconds() {
        int freeTime = 0;
        for (Day day : days) {
            freeTime += day.getFreeTimeInSeconds();
        }
        return freeTime;
    }

    public void printSchedule() {
        System.out.println("Schedule for " + from + " to " + to);
        System.out.println("========================================");
        System.out.println("Free time: " + getFreeTimeInSeconds() / 60 / 60 + " hours");
        System.out.println("========================================");

        CommandLineTable table = new CommandLineTable();
        List<Day[]> weeklyDays = Day.splitDaysIntoWeeks(days);

        table.setHeaders("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

        for (Day[] week : weeklyDays) {
            table.setShowVerticalLines(true);

            for (Day day : week) {
                for (int hour = 0; hour < 24; hour++) {
                    String hourString = hour + ":00";
                    if (hour < 10) {
                        hourString = "0" + hourString;
                    }
                    table.addRow(hourString, day.getTaskForHour(hour));
                }
            }

            table.print();
        }
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public Day[] getAgendas() {
        return days;
    }
}
