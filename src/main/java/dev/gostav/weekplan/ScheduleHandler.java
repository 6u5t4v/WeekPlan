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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleHandler {
    private final JSONParser parser = new JSONParser();
    private DefaultDay workDay, offDay;
    private Map<Goal, Integer> goals = new HashMap<>();
    private List<DefaultDay> DAILY_SCHEDULES = new ArrayList<>();

    public Schedule createSchedule(LocalDate startDate, int days) {
        Agenda[] dailyAgendas = new Agenda[days];

        for (int i = 0; i < days; i++) {
            LocalDate date = startDate.plusDays(i);
            if (isWorkday(date)) {
                dailyAgendas[i] = new Agenda(workDay);
            } else {
                dailyAgendas[i] = new Agenda(offDay);
            }

            Agenda agenda = dailyAgendas[i];

            // figure out how much time is available between routines
            float freeTime = agenda.getDefaultDay().freeTimeInSeconds();
            float hours = freeTime / 3600;
            System.out.println(date + ": " + hours + " free time");

            if (hours < 0) {
                throw new RuntimeException("Free time is negative, not good");
            }

            List<Goal> dailyGoals = getDailyGoals();

            for (Goal goal : dailyGoals) {
                float hoursPrDay = (float) goal.getHoursPrWeek() / 7;

                if (hoursPrDay > hours) {
                    throw new RuntimeException("Not enough time to meet daily goal " + goal.getTitle());
                }

                hours -= hoursPrDay;
            }

            while (hours > 0) {
                Goal goal = randomGoal();
                int hoursPrWeek = goal.getHoursPrWeek();
            }

            // all the free time has to be divided into goals


            System.out.println("Hours left: " + hours);

            // the goals have to have their minimum time requirement met

            // if the filler task minimum time exceeds a threshold it has to be
            // split across multiple days
        }

        return new Schedule(startDate, startDate.plusDays(days), dailyAgendas);
    }

    private Goal randomGoal() {
        int index = (int) (Math.random() * goals.size());
        return (Goal) goals.keySet().toArray()[index];
    }

    private List<Goal> getDailyGoals() {
        List<Goal> dailyGoals = new ArrayList<>();

        for (Map.Entry<Goal, Integer> entry : goals.entrySet()) {
            Goal goal = entry.getKey();
            if (goal.isDaily()) {
                dailyGoals.add(goal);
            }
        }

        return dailyGoals;
    }

    private Goal getGoalWithLowestHoursPrWeek() {
        Goal goal = null;
        int lowestHoursPrWeek = Integer.MAX_VALUE;

        for (Map.Entry<Goal, Integer> entry : goals.entrySet()) {
            Goal g = entry.getKey();
            int hoursPrWeek = g.getHoursPrWeek();
            if (hoursPrWeek < lowestHoursPrWeek) {
                lowestHoursPrWeek = hoursPrWeek;
                goal = g;
            }
        }

        return goal;
    }

    public void initGoals(File file) {
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
            goals.put(t, 0);
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
            long minBreaks = (long) schedule.get("min_breaks");

            JSONArray jsonRoutinesArray = (JSONArray) schedule.get("routines");

            DefaultDay defaultDefaultDay = new DefaultDay(name, description, (int) minBreaks);
            DAILY_SCHEDULES.add(defaultDefaultDay);

            List<Routine> routines = loadRoutines(jsonRoutinesArray);
            defaultDefaultDay.setRoutines(routines);
        }

        workDay = DAILY_SCHEDULES.get(0);
        offDay = DAILY_SCHEDULES.get(1);
        System.out.println("Schedules: " + DAILY_SCHEDULES.size());
    }

    private List<Routine> loadRoutines(JSONArray json) {
        List<Routine> rountines = new ArrayList<>();

        for (Object rObj : json) {
            JSONObject routine = (JSONObject) rObj;

            String routineName = (String) routine.get("name");
            LocalTime startTime = LocalTime.parse((String) routine.get("start"));
            LocalTime endTime = LocalTime.parse((String) routine.get("end"));

            Routine r = new Routine(routineName, startTime, endTime);

            JSONArray jsonTasks = (JSONArray) routine.get("tasks");

            List<Task> tasks = loadTasks(jsonTasks);
            r.setTasks(tasks);

            rountines.add(r);
        }

        return rountines;
    }

    private List<Task> loadTasks(JSONArray json) {
        List<Task> tasks = new ArrayList<>();

        for (Object o : json) {
            String taskName = (String) o;

            Task task = goals.keySet().stream()
                    .filter(g -> g.getTitle().equals(taskName))
                    .findFirst()
                    .orElse(new Goal(taskName, "", 0, false));

            tasks.add(task);
        }

        return tasks;
    }


    private boolean isWorkday(LocalDate date) {
        return date.getDayOfWeek().getValue() < 6;
    }
}
