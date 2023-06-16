package dev.gostav.weekplan;

import dev.gostav.weekplan.model.Goal;
import dev.gostav.weekplan.model.Task;

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

    Map<Integer, List<Task>> schedule = new HashMap<>();

    private void initializeGoals() {
        // Add your goals with their respective minimum hours per week
        GOALS.add(new Goal("Goal 1", 10, true));  // Daily goal
        GOALS.add(new Goal("Goal 2", 10, false));  // Non-daily goal
        GOALS.add(new Goal("Goal 3", 10, false));  // Non-daily goal
        GOALS.add(new Goal("Goal 4", 10, true));   // Daily goal
        GOALS.add(new Goal("Goal 5", 10, false));  // Non-daily goal
        GOALS.add(new Goal("Goal 6", 10, true));   // Daily goal

        final int hoursInAWeek = 168;
        if (getTotalGoalsHours() > hoursInAWeek) {
            throw new RuntimeException("Total hours of goals exceed the number of hours in a week");
        }
    }

    private List<Task> createDaySchedule() {
        List<Task> tasks = new ArrayList<>();

        int timeOfDay = 0;
        while (timeOfDay < ROUTINES.length - 1) {
            LocalTime time = ROUTINES[timeOfDay];

            Goal goal = selectGoal();

            if (goal == null) {
                System.out.println("All goals have been scheduled");
                break;
            }

            int secondsRemaining = goal.getSecondsRemaining();

            try {
                LocalTime nextRoutine = ROUTINES[timeOfDay + 1];
                long untilNextTask = time.until(nextRoutine, ChronoUnit.SECONDS);

                secondsRemaining = Math.min(secondsRemaining, (int) (untilNextTask - 60 * 30));
            } catch (Exception e) {
                System.out.println("No more routines for today");
            }

            goal.subtractSeconds(secondsRemaining);

            int hours = secondsRemaining / 3600;
            Task task = new Task(goal, time, time.plusHours(hours));
            tasks.add(task);

            timeOfDay++;
        }

        return tasks;
    }

    public Map<Integer, List<Task>> createSchedule() {
        for (int day = 1; day <= 7; day++) {
            schedule.put(day, createDaySchedule());
        }

        return schedule;
    }

    private List<Goal> getDailyGoals() {
        List<Goal> dailyGoals = new ArrayList<>();
        for (Goal g : GOALS) {
            if (g.isDailyGoal()) {
                dailyGoals.add(g);
            }
        }

        return dailyGoals;
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
            return null;
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
