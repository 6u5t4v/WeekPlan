package dev.gostav.weekplan;

import dev.gostav.weekplan.model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleHandler {
    private final JSONParser parser = new JSONParser();
    private DefaultDay workDay, offDay;
    private List<Goal> goals = new ArrayList<>();
    private List<DefaultDay> dailySchedules = new ArrayList<>();

    public Schedule createSchedule(LocalDate startDate, int days) {
        Day[] dailyDays = new Day[days];

        for (int i = 0; i < days; i++) {
            LocalDate date = startDate.plusDays(i);
            if (isWorkday(date)) {
                dailyDays[i] = new Day(date, workDay);
            } else {
                dailyDays[i] = new Day(date, offDay);
            }

            Day day = dailyDays[i];

            // figure out how much time is available between routines
            float freeTime = day.getFreeTimeInSeconds();
            float hoursRemaining = freeTime / 3600;
            System.out.println(date + ": " + hoursRemaining + " free time");

            if (hoursRemaining < 0) {
                throw new RuntimeException("Free time is negative, not good");
            }

            // Accounting for daily goals that has to be met every day
            List<Goal> dailyGoals = getDailyGoals();
            for (Goal goal : dailyGoals) {
                float hoursPrDay = (float) goal.getHoursPrWeek() / 7;

                if (hoursPrDay > hoursRemaining) {
                    throw new RuntimeException("Not enough time to meet daily goal " + goal.getName());
                }

                hoursRemaining -= hoursPrDay;
            }

            // Accounting for weekly goals that has to be met every week
            int maxTasksPrDay = 4;
            while (hoursRemaining >= 0) {
                Goal goal = randomGoal();
                if (day.getTasks().size() == maxTasksPrDay) {
                    // if there are more than 4 tasks already, don't add more
                    // tasks
                    break;
                }

                float hoursPrDay = goal.getHoursPrWeek() % hoursRemaining + hoursRemaining;

                if (hoursPrDay >= 4) {
                    // if the goal has more than 4 hours pr day, it has to be
                    // split across the day
                    hoursPrDay = ((float) goal.getHoursPrWeek() / 7) % hoursRemaining;
                }

                goal.setHoursFullFilled(goal.getHoursFullFilled() + hoursPrDay);

                Task task = new Task(goal, LocalTime.of(0, 0));
            }

//            while (hoursRemaining >= 0) {
//                Goal goal = randomGoal();
//
//                float hoursPrDay = goal.getHoursPrWeek() % hoursRemaining + hoursRemaining;
////                System.out.println(hoursRemaining + " hours left, adding " + hoursPrDay + " hours of " + goal.getTitle());
//
//                if (hoursPrDay >= 4) {
//                    // if the goal has more than 4 hours pr day, it has to be
//                    // split across multiple days
//                    hoursPrDay = ((float) goal.getHoursPrWeek() / 7) % hoursRemaining;
//                }
//
//                hoursRemaining -= hoursPrDay;
//
//                float finalHoursPrDay = hoursPrDay;
//                goal.setHoursFullFilled(goal.getHoursFullFilled() + finalHoursPrDay);
//
//                day.addTask(goal);
//            }

            System.out.println("Hours left: " + hoursRemaining);
        }

        return new Schedule(startDate, startDate.plusDays(days), dailyDays);
    }

    private Goal randomGoal() {
        if (goals.isEmpty()) {
            throw new RuntimeException("No goals");
        }

        List<Goal> validGoals = notFullfilledGoals();
        if (validGoals.isEmpty()) {
            validGoals = new ArrayList<>(goals);
        }

        int index = (int) (Math.random() * validGoals.size());
        return (Goal) validGoals.toArray()[index];
    }

    private List<Goal> notFullfilledGoals() {
        return goals.stream()
                .filter(g -> g.getHoursFullFilled() < g.getHoursPrWeek())
                .toList();
    }

    private List<Goal> getDailyGoals() {
        return goals.stream().filter(Goal::isDaily).toList();
    }

    public void initTasks(File file) {
        JSONArray ja;
        try {
            Object json = parser.parse(new FileReader(file));
            ja = (JSONArray) json;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        for (Object o : ja) {
            JSONObject task = (JSONObject) o;

            String title = (String) task.get("title");
            String description = (String) task.getOrDefault("description", "");
            long minHoursPrWeek = (long) task.get("min_hours_pr_week");
            boolean isDaily = (boolean) task.getOrDefault("daily", false);

            Goal t = new Goal(title, description, (int) minHoursPrWeek, isDaily);
            goals.add(t);
        }

        System.out.println("Goals: " + goals.size());
    }

    public void initDefaultSchedules(File file) {
        JSONArray ja;
        try {
            Object json = parser.parse(new FileReader(file));
            ja = (JSONArray) json;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        for (Object o : ja) {
            JSONObject schedule = (JSONObject) o;

            String name = (String) schedule.get("name");
            String description = (String) schedule.get("description");

            JSONArray jsonRoutinesArray = (JSONArray) schedule.get("routines");

            DefaultDay defaultDefaultDay = new DefaultDay(name, description);
            dailySchedules.add(defaultDefaultDay);

            List<Routine> routines = loadRoutines(jsonRoutinesArray);
            defaultDefaultDay.setRoutines(routines);
        }

        workDay = dailySchedules.get(0);
        offDay = dailySchedules.get(1);
        System.out.println("Schedules: " + dailySchedules.size());
    }

    private List<Routine> loadRoutines(JSONArray json) {
        List<Routine> rountines = new ArrayList<>();

        for (Object rObj : json) {
            JSONObject routine = (JSONObject) rObj;

            String routineName = (String) routine.get("name");
            LocalTime startTime = LocalTime.parse((String) routine.get("start"));

            Routine r = new Routine(routineName, startTime);

            rountines.add(r);
        }

        return rountines;
    }

//    private List<Task> loadTasks(JSONArray json) {
//        List<Task> tasks = new ArrayList<>();
//
//        for (Object o : json) {
//            String taskName = (String) o;
//
//            Task task = goals.stream()
//                    .filter(g -> g.getName().equals(taskName))
//                    .findFirst()
//                    .orElse(new Goal(taskName, "", 0, false));
//
//            tasks.add(task);
//        }
//
//        return tasks;
//    }


    private boolean isWorkday(LocalDate date) {
        return date.getDayOfWeek().getValue() < 6;
    }
}
