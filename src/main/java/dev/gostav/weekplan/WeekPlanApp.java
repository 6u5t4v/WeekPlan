package dev.gostav.weekplan;

import dev.gostav.weekplan.model.Schedule;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.previousOrSame;

public class WeekPlanApp {
    public static void main(String[] args) {
        File tasksFile = new File("src/main/resources/goals.json");
        File schedulesFile = new File("src/main/resources/schedules.json");

        ScheduleHandler handler = new ScheduleHandler();
        handler.initGoals(tasksFile);
        handler.initDefaultSchedules(schedulesFile);

        LocalDate startDate = LocalDate.now().with(previousOrSame(DayOfWeek.MONDAY));
        Schedule schedule = handler.createSchedule(startDate, 7);
    }
}