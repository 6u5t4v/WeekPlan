package dev.gostav.weekplan;

import dev.gostav.weekplan.model.Schedule;

import java.io.File;
import java.time.LocalDate;

public class WeekPlanApp {
    public static void main(String[] args) {
        File tasksFile = new File("src/main/resources/goals.json");
        File schedulesFile = new File("src/main/resources/schedules.json");

        ScheduleHandler handler = ScheduleHandler.getInstance();
        Schedule schedule = handler.createSchedule(LocalDate.now(), LocalDate.now().plusDays(7));

        schedule.printSchedule();
        handler.printGoalTimes();
        System.out.println();
        handler.printTotalHoursScheduled(schedule);


    }
}