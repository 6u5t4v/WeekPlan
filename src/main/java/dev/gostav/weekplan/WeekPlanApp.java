package dev.gostav.weekplan;

import dev.gostav.weekplan.model.Schedule;

import java.io.File;
import java.time.LocalDate;

public class WeekPlanApp {
    public static void main(String[] args) {
        File goalsFile = new File("src/main/resources/goals.json");
        File schedulesFile = new File("src/main/resources/schedules.json");

        ScheduleHandler handler = new ScheduleHandler(goalsFile, schedulesFile);
        Schedule schedule = handler.createSchedule(LocalDate.now(), LocalDate.now().plusDays(7));

        schedule.printTable();
        handler.printGoalTimes();
        System.out.println();
        handler.printTotalHoursScheduled(schedule);
    }
}