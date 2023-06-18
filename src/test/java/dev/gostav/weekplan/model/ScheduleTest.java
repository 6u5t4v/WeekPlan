package dev.gostav.weekplan.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScheduleTest {
    private Schedule schedule;
    private LocalDate startDate;
    private LocalDate endDate;
    private Map<Integer, List<Task>> tasksByDay;

    @BeforeEach
    void setUp() {
        startDate = LocalDate.of(2023, 6, 1);
        endDate = LocalDate.of(2023, 6, 7);
        tasksByDay = new HashMap<>();

        // Create sample tasks for each day
        for (int day = 1; day <= 7; day++) {
            List<Task> tasks = new ArrayList<>();
            Goal goal = new Goal("Test Goal", 10, false, 8);
            LocalTime startTime = LocalTime.of(9, 0);
            LocalTime endTime = LocalTime.of(12, 30);
            Task task = new Task(goal, startTime, endTime);
            tasks.add(task);
            tasksByDay.put(day, tasks);
        }

        schedule = new Schedule(startDate, endDate, tasksByDay);
    }

    @Test
    void getTotalHours() {
        int expectedTotalHours = 7 * 3; // 7 days * 3 hours per day
        assertEquals(expectedTotalHours, schedule.getTotalHours());
    }

    @Test
    void getStartDate() {
        assertEquals(startDate, schedule.getStartDate());
    }

    @Test
    void getEndDate() {
        assertEquals(endDate, schedule.getEndDate());
    }
}
