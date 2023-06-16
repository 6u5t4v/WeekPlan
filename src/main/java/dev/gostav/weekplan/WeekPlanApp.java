package dev.gostav.weekplan;

import dev.gostav.weekplan.model.Task;

import java.util.List;
import java.util.Map;

public class WeekPlanApp {
//    public static void main(String[] args) {
//        File tasksFile = new File("src/main/resources/goals.json");
//        File schedulesFile = new File("src/main/resources/schedules.json");
//
//        ScheduleHandler handler = new ScheduleHandler();
//        handler.initTasks(tasksFile);
//        handler.initDefaultSchedules(schedulesFile);
//
//        LocalDate startDate = LocalDate.now().with(previousOrSame(DayOfWeek.MONDAY));
//        Schedule schedule = handler.createSchedule(startDate, 7);
//        schedule.printSchedule();
//    }


    public static void main(String[] args) {
        ScheduleHandler handler = ScheduleHandler.getInstance();
        Map<Integer, List<Task>> schedule = handler.createSchedule();

        // Display the schedule
        for (Map.Entry<Integer, List<Task>> entry : schedule.entrySet()) {
            int day = entry.getKey();
            List<Task> tasks = entry.getValue();

            System.out.println("Day " + day + ":");
            for (Task task : tasks) {
                System.out.println("    " + task.getGoal().getName() + ": " + task.getStartTime() + " - " + task.getEndTime() + " (" + task.getHours() + " hours)");
            }
            System.out.println();
        }
    }
}