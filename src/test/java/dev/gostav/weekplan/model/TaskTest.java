package dev.gostav.weekplan.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {
    private Goal goal;
    private LocalTime startTime;
    private LocalTime endTime;
    private Task task;

    @BeforeEach
    void setUp() {
        goal = new Goal("Test Goal", 10, false, 8);
        startTime = LocalTime.of(9, 0);
        endTime = LocalTime.of(12, 30);
        task = new Task(goal, startTime, endTime);
    }

    @Test
    void getHours() {
        float expectedHours = 3.5f;
        assertEquals(expectedHours, task.getHours());
    }
}