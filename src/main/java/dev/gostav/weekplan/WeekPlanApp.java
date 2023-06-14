package dev.gostav.weekplan;

import dev.gostav.weekplan.model.Goal;
import dev.gostav.weekplan.model.Routine;
import dev.gostav.weekplan.model.Task;
import dev.gostav.weekplan.model.layouts.DefaultDay;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class WeekPlanApp {
    private static final List<Goal> GOALS = new ArrayList<>();
    private static final List<DefaultDay> DAILY_SCHEDULES = new ArrayList<>();

    private static final JSONParser PARSER = new JSONParser();


    public static void main(String[] args) {
        File tasksFile = new File("src/main/resources/goals.json");
        File schedulesFile = new File("src/main/resources/schedules.json");

        try {
            FileReader reader = new FileReader(tasksFile);
            Object json = PARSER.parse(reader);

            Goals((JSONArray) json);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        try {
            FileReader reader = new FileReader(schedulesFile);
            Object json = PARSER.parse(reader);
            DailySchedules((JSONArray) json);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        new ScheduleHandler(DAILY_SCHEDULES.get(0), DAILY_SCHEDULES.get(1));

        System.out.println("Schedules: " + DAILY_SCHEDULES.size());
        System.out.println("Goals: " + GOALS.size());
    }

    private static void DailySchedules(JSONArray jsonObject) {
        for (Object o : jsonObject) {
            JSONObject schedule = (JSONObject) o;

            String name = (String) schedule.get("name");
            String description = (String) schedule.get("description");
            long minBreaks = (long) schedule.get("min_breaks");

            JSONArray jsonRoutinesArray = (JSONArray) schedule.get("routines");

            DefaultDay defaultDefaultDay = new DefaultDay(name, description, (int) minBreaks);
            DAILY_SCHEDULES.add(defaultDefaultDay);

            List<Routine> routines = Routines(jsonRoutinesArray);
            defaultDefaultDay.setRoutines(routines);
        }
    }

    private static List<Routine> Routines(JSONArray json) {
        List<Routine> rountines = new ArrayList<>();

        for (Object rObj : json) {
            JSONObject routine = (JSONObject) rObj;

            String routineName = (String) routine.get("name");
            LocalTime startTime = LocalTime.parse((String) routine.get("start"));
            LocalTime endTime = LocalTime.parse((String) routine.get("end"));

            Routine r = new Routine(routineName, startTime, endTime);

            JSONArray jsonTasks = (JSONArray) routine.get("tasks");

            List<Task> tasks = Tasks(jsonTasks);
            r.setTasks(tasks);

            rountines.add(r);
        }

        return rountines;
    }

    private static List<Task> Tasks(JSONArray json) {
        List<Task> tasks = new ArrayList<>();

        for (Object o : json) {
            String taskName = (String) o;

            Task task = GOALS.stream()
                    .filter(goal -> goal.getTitle().equals(taskName))
                    .findFirst()
                    .orElse(new Goal(taskName, "", 0, false));

            tasks.add(task);
        }

        return tasks;
    }

    private static void Goals(JSONArray json) {
        for (Object o : json) {
            JSONObject task = (JSONObject) o;

            String title = (String) task.get("title");
            String description = (String) task.getOrDefault("description", "");
            long minHoursPrWeek = (long) task.get("min_hours_pr_week");
            boolean isDaily = (boolean) task.getOrDefault("daily", false);

            Goal t = new Goal(title, description, (int) minHoursPrWeek, isDaily);
            GOALS.add(t);
        }
    }
}