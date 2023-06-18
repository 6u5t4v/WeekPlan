package dev.gostav.weekplan.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoalTest {
    private Goal goal;

    @BeforeEach
    void setUp() {
        goal = new Goal("Test Goal", 10, false, 8);
    }

    @Test
    void addHours() {
        goal.addHours(5.5f);
        assertEquals(5.5f, goal.getHoursScheduled());
    }

    @Test
    void isComplete() {
        assertFalse(goal.isComplete());

        goal.addHours(10);
        assertTrue(goal.isComplete());
    }

    @Test
    void getHoursScheduled() {
        goal.addHours(2.5f);
        assertEquals(2.5f, goal.getHoursScheduled());
    }
}
