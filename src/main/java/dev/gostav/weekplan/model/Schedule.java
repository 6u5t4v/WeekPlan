package dev.gostav.weekplan.model;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Schedule {
    private LocalDate startDate, endDate;
    private Map<Integer, List<Task>> schedule;

    public Schedule(LocalDate startDate, LocalDate endDate, Map<Integer, List<Task>> schedule) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.schedule = schedule;
    }

    public void printSchedule() {
        System.out.println("Schedule from " + startDate + " to " + endDate + ":");
        for (Map.Entry<Integer, List<Task>> entry : schedule.entrySet()) {
            int day = entry.getKey();
            List<Task> tasks = entry.getValue();

            String dayOfWeek = startDate.plusDays(day - 1).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            System.out.println("Day " + day + " " + dayOfWeek + ":");
            for (Task task : tasks) {
                System.out.println("    " + task.formatted());
            }
            System.out.println();
        }
    }

    public void printTable() {
        CommandLineTable table = new CommandLineTable();
        table.setShowVerticalLines(true);

        String[] dates = startDate.datesUntil(endDate)
                .toList()
                .stream()
                .map(d -> d.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH))
                .toArray(String[]::new);

        table.setHeaders(dates);


        // Make sure only 1 week per table
        for (int i = 0; i < 4; i++) {
            String[] cells = new String[dates.length];

            for (int day = 1; day <= dates.length; day++) {
                Task tasksForToday;
                try {
                    tasksForToday = schedule.get(day).get(i);
                    cells[day - 1] = tasksForToday.toString();

                } catch (Exception e) {
                    cells[day - 1] = "";
                }
            }

            table.addRow(cells);
        }


        table.print();
    }

    private List<Task> allTasks() {
        List<Task> tasks = new ArrayList<>();
        for (Map.Entry<Integer, List<Task>> entry : schedule.entrySet()) {
            tasks.addAll(entry.getValue());
        }

        return tasks;
    }

    public int getTotalHours() {
        int totalHours = 0;
        for (Map.Entry<Integer, List<Task>> entry : schedule.entrySet()) {
            List<Task> tasks = entry.getValue();
            for (Task task : tasks) {
                totalHours += task.getHours();
            }
        }

        return totalHours;

    }

    private void getGoalTimes() {

    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
