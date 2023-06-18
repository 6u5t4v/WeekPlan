package dev.gostav.weekplan;

import dev.gostav.weekplan.model.Goal;
import dev.gostav.weekplan.model.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleHandlerTest {
    private ScheduleHandler scheduleHandler;
    private File goalsFile;
    private File schedulesFile;

    @BeforeEach
    void setUp() {
        goalsFile = new File("src/main/resources/goals.json");
        schedulesFile = new File("src/main/resources/schedules.json");
        scheduleHandler = new ScheduleHandler(goalsFile, schedulesFile);
    }

    @Test
    void createDaySchedule() {
        LocalDate date = LocalDate.of(2023, 6, 1);
        assertNotNull(scheduleHandler.createDaySchedule(date));
        assertFalse(scheduleHandler.createDaySchedule(date).isEmpty());
    }

    @Test
    void isOffday() {
        LocalDate offdayDate = LocalDate.of(2023, 6, 4);
        assertTrue(scheduleHandler.isOffday(offdayDate));

        LocalDate weekdayDate = LocalDate.of(2023, 6, 5);
        assertFalse(scheduleHandler.isOffday(weekdayDate));
    }

    @Test
    void createSchedule() {
        LocalDate startDate = LocalDate.of(2023, 6, 1);
        LocalDate endDate = LocalDate.of(2023, 6, 7);
        Schedule schedule = scheduleHandler.createSchedule(startDate, endDate);
        assertNotNull(schedule);
    }

    @Test
    void getTotalGoalsHours() {
        int totalGoalsHours = scheduleHandler.getTotalGoalsHours();
        assertTrue(totalGoalsHours >= 0);
    }

    @Test
    void selectGoal() {
        Goal goal = scheduleHandler.selectGoal();
        assertNotNull(goal);
    }

    @Test
    void getTotalHoursScheduled() {
        LocalDate startDate = LocalDate.of(2023, 6, 1);
        LocalDate endDate = LocalDate.of(2023, 6, 7);
        Schedule schedule = scheduleHandler.createSchedule(startDate, endDate);
        assertNotNull(schedule);
        int totalHoursScheduled = scheduleHandler.getTotalHoursScheduled(schedule);
        assertTrue(totalHoursScheduled >= 0);
    }

    @Test
    void getGoals() {
        assertNotNull(scheduleHandler.getGoals());
        assertFalse(scheduleHandler.getGoals().isEmpty());
    }

    @Test
    void getPlans() {
        assertNotNull(scheduleHandler.getPlans());
        assertFalse(scheduleHandler.getPlans().isEmpty());
    }
}
