package dev.gostav.weekplan;

import dev.gostav.weekplan.model.Goal;
import dev.gostav.weekplan.model.Schedule;
import dev.gostav.weekplan.model.Task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScheduleHandler {
    public static ScheduleHandler instance;

    private final List<Goal> GOALS = new ArrayList<>();
    private final LocalTime[] ROUTINES = {
            LocalTime.of(10, 0),
            LocalTime.of(14, 0),
            LocalTime.of(17, 0),
            LocalTime.of(20, 30),
            LocalTime.of(23, 0),

    };

    private void initializeGoals() {
        // Add your goals with their respective minimum hours per week
        GOALS.add(new Goal("Hobby Project", 10, true));  // Daily goal
        GOALS.add(new Goal("Time sensitive Project", 10, false));  // Non-daily goal
        GOALS.add(new Goal("Practice Guitar", 7, false));  // Non-daily goal
        GOALS.add(new Goal("Marketing Company", 10, true));   // Daily goal
        GOALS.add(new Goal("Outside", 20, false));  // Non-daily goal
        GOALS.add(new Goal("Gaming", 8, true));   // Daily goal

        final int hoursInAWeek = 168;
        if (getTotalGoalsHours() > hoursInAWeek) {
            throw new RuntimeException("Total hours of goals exceed the number of hours in a week");
        }
    }

    private List<Task> createDaySchedule() {
        List<Task> tasks = new ArrayList<>();

        int timeOfDay = 0;

        Goal goal = null;
        int maxSeconds = 0;

        while (timeOfDay < ROUTINES.length - 1) {
            LocalTime time = ROUTINES[timeOfDay], nextRoutine = ROUTINES[timeOfDay + 1];

            int seconds;

            // the subtraction of 30 minutes is to make sure that the next routine is not too close
            long untilNextTask = time.until(nextRoutine, ChronoUnit.SECONDS) - 60 * 30;

            if (maxSeconds < 3600) {
                maxSeconds = (int) (Math.random() * 3600 * 5) + 3600;
                goal = null;
            }

            seconds = (int) Math.min(maxSeconds, untilNextTask);
            maxSeconds -= seconds;

            if (goal == null) {
                goal = selectGoal();
            }

            goal.scheduleWithSeconds(seconds);

            int hours = seconds / 3600;
            Task task = new Task(goal, time, time.plusHours(hours));
            tasks.add(task);

            timeOfDay++;
        }

        return tasks;
    }

    public void printTotalHoursScheduled(Schedule schedule) {
        System.out.println("Total hours scheduled: " + schedule.getTotalHours() + " / " + getTotalGoalsHours());
    }

    public void printGoalTimes() {
        for (Goal g : GOALS) {
            System.out.println(g.getName() + ": " + g.getHoursScheduled() + " hours" + (g.isComplete() ? " (Complete)" : ""));
        }
    }

    public Schedule createSchedule(LocalDate startDate, LocalDate endDate) {
        final Map<Integer, List<Task>> schedule = new HashMap<>();

        int days = (int) ChronoUnit.DAYS.between(startDate, endDate);
        for (int i = 1; i <= days; i++) {
            List<Task> tasks = createDaySchedule();
            schedule.put(i, tasks);
        }

        return new Schedule(startDate, endDate, schedule);
    }

    private int getTotalGoalsHours() {
        int totalHours = 0;
        for (Goal g : GOALS) {
            totalHours += g.getMinHoursPerWeek();
        }

        return totalHours;
    }

    private Goal selectGoal() {
        List<Goal> validGoals = GOALS.stream().filter(g -> !g.isComplete()).toList();

        if (validGoals.isEmpty()) {
            validGoals = new ArrayList<>(GOALS);
        }

        int randomIndex = (int) (Math.random() * validGoals.size());
        return validGoals.get(randomIndex);
    }

    public static ScheduleHandler getInstance() {
        if (instance == null) {
            instance = new ScheduleHandler();
        }
        return instance;
    }

    private ScheduleHandler() {
        initializeGoals();
    }
}
