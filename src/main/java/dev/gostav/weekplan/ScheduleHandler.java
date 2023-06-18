package dev.gostav.weekplan;

import dev.gostav.weekplan.model.DayPlan;
import dev.gostav.weekplan.model.Goal;
import dev.gostav.weekplan.model.Schedule;
import dev.gostav.weekplan.model.Task;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScheduleHandler {
    private static JSONParser JsonParser;

    private final List<Goal> goals = new ArrayList<>();
    private List<DayPlan> plans = new ArrayList<>();

    public ScheduleHandler(File goalsFile, File schedulesFile) {
        JsonParser = new JSONParser();
        try {
            FileReader goalsReader = new FileReader(goalsFile);
            Object goalsObject = JsonParser.parse(goalsReader);
            initializeGoals(goalsObject);

            FileReader schedulesReader = new FileReader(schedulesFile);
            Object schedules = JsonParser.parse(schedulesReader);
            initializeSchedules(schedules);

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeGoals(Object jsonObject) {
        JSONArray json = (JSONArray) jsonObject;

        for (Object o : json) {
            JSONObject task = (JSONObject) o;

            String name = (String) task.get("name");
            long minHoursPrWeek = (long) task.get("min_hours_pr_week");
            boolean isDaily = (boolean) task.getOrDefault("daily", false);
            long maxHoursPrDay = (long) task.getOrDefault("max_hours_pr_day", -1);

            Goal t = new Goal(name, (int) minHoursPrWeek, isDaily, (int) maxHoursPrDay);
            goals.add(t);
        }


        final int hoursInAWeek = 168;
        if (getTotalGoalsHours() > hoursInAWeek) {
            throw new RuntimeException("Total hours of goals exceed the number of hours in a week");
        }
    }

    private void initializeSchedules(Object object) {
        JSONArray json = (JSONArray) object;

        for (Object obj : json) {
            JSONObject jsonObject = (JSONObject) obj;

            String name = jsonObject.get("name").toString();
            String description = jsonObject.get("description").toString();
            LocalTime wakeUpTime = LocalTime.parse(jsonObject.get("wake_up_time").toString());
            LocalTime bedTime = LocalTime.parse(jsonObject.get("bed_time").toString());
            JSONArray routines = (JSONArray) jsonObject.get("routines");

            LocalTime[] routinesArr = new LocalTime[routines.size()];
            for (int i = 0; i < routines.size(); i++) {
                routinesArr[i] = LocalTime.parse(routines.get(i).toString());
            }

            plans.add(new DayPlan(name, description, wakeUpTime, bedTime, routinesArr));
        }
    }

    private List<Task> createDaySchedule(LocalDate date) {
        List<Task> tasks = new ArrayList<>();
        List<Goal> dailyGoals = new ArrayList<>();

        int timeOfDay = 0;

        Goal goal = null;
        int maxSeconds = 0;

        // this is sooo dumb, but it works for now
        int dayPlan = isOffday(date) ? 1 : 0;
        LocalTime[] routines = plans.get(dayPlan).getRoutines();

        while (timeOfDay < routines.length) {
            LocalTime time = routines[timeOfDay];
            LocalTime nextRoutine;
            try {
                nextRoutine = routines[timeOfDay + 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                nextRoutine = plans.get(dayPlan).getNight();
            }

            // amount of seconds to spend on this goal during this routine
            int seconds;

            // the subtraction of 30 minutes is to make sure that the next routine is not too close
            long untilNextTask = time.until(nextRoutine, ChronoUnit.SECONDS) - Time.toSeconds(0.5f);

            // if there is less than 1 hour left from previous routine, reset maxSeconds
            if (maxSeconds < 3600) {
                // max seconds is a random number between 1 and 5 hours
                maxSeconds = (int) (Math.random() * (Time.toSeconds(5))) + 3600;
                goal = null;
            }


            seconds = (int) Math.min(maxSeconds, untilNextTask);
            maxSeconds -= seconds;

            // goal is null if it is the first routine of the day or if the maxSeconds has been reached
            if (goal == null) {
                // select a random goal that has not been completed
                goal = selectGoal();
            }

            if (goal.getMaxHoursPerDay() != -1 && seconds > Time.toSeconds(goal.getMaxHoursPerDay())) {
                seconds = Time.toSeconds(goal.getMaxHoursPerDay());
                maxSeconds = 0;
            }

            goal.scheduleWithSeconds(seconds);

            Task task = new Task(goal, time, time.plusSeconds(seconds));
            tasks.add(task);
            dailyGoals.add(goal);

            timeOfDay++;
        }

        return tasks;
    }

    private boolean isOffday(LocalDate date) {
        return date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7;
    }

    public void printTotalHoursScheduled(Schedule schedule) {
        System.out.println("Total hours scheduled: " + schedule.getTotalHours() + " / " + getTotalGoalsHours());
    }

    public void printGoalTimes() {
        for (Goal g : goals) {
            System.out.println(g.getName() + ": " + g.getHoursScheduled() + " hours" + (g.isComplete() ? " (Complete)" : ""));
        }
    }

    public Schedule createSchedule(LocalDate startDate, LocalDate endDate) {
        final Map<Integer, List<Task>> schedule = new HashMap<>();

        int days = (int) ChronoUnit.DAYS.between(startDate, endDate);
        for (int i = 0; i < days; i++) {
            List<Task> tasks = createDaySchedule(startDate.plusDays(i));
            schedule.put(i + 1, tasks);
        }

        return new Schedule(startDate, endDate, schedule);
    }

    private int getTotalGoalsHours() {
        int totalHours = 0;
        for (Goal g : goals) {
            totalHours += g.getMinHoursPerWeek();
        }

        return totalHours;
    }

    private Goal selectGoal() {
        List<Goal> validGoals = goals.stream().filter(g -> !g.isComplete()).toList();


        int randomIndex = (int) (Math.random() * validGoals.size());
        return validGoals.get(randomIndex);
    }
}
