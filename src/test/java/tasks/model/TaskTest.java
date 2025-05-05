package tasks.model;

import org.junit.Before;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

class TaskTest {

    private Task task;
    private Calendar calendar;

    @Before
    public void setUp() {
        calendar = Calendar.getInstance();
    }

    private Date createDate(int year, int month, int day, int hour, int minute) {
        calendar.set(year, month, day, hour, minute, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    @Test
    void testPath_P01_CurrentAfterOrEqualsEnd() {
        Date startTime = createDate(2023, Calendar.JANUARY, 1, 10, 0);
        Date endTime = createDate(2023, Calendar.JANUARY, 10, 10, 0);
        int interval = 24 * 60 * 60;

        task = new Task("Test Task", startTime, endTime, interval);
        task.setActive(true);

        Date currentTime = createDate(2023, Calendar.JANUARY, 10, 10, 0);
        assertNull("Should return null when current equals end", task.nextTimeAfter(currentTime));

        currentTime = createDate(2023, Calendar.JANUARY, 11, 10, 0);
        assertNull("Should return null when current is after end", task.nextTimeAfter(currentTime));
    }

    @Test
    void testPath_P02_RepeatedActiveBeforeStart() {
        Date startTime = createDate(2023, Calendar.JANUARY, 1, 10, 0);
        Date endTime = createDate(2023, Calendar.JANUARY, 10, 10, 0);
        int interval = 24 * 60 * 60;

        task = new Task("Test Task", startTime, endTime, interval);
        task.setActive(true);

        Date currentTime = createDate(2022, Calendar.DECEMBER, 31, 10, 0);

        assertEquals("Should return start time when current is before start",
                startTime, task.nextTimeAfter(currentTime));
    }

    @Test
    void testPath_P03_RepeatedActiveEqualsTimeAfter() {
        Date startTime = createDate(2023, Calendar.JANUARY, 1, 10, 0);
        Date endTime = createDate(2023, Calendar.JANUARY, 10, 10, 0);
        int interval = 24 * 60 * 60;

        task = new Task("Test Task", startTime, endTime, interval);
        task.setActive(true);

        Date currentTime = createDate(2023, Calendar.JANUARY, 1, 10, 0);
        Date expectedNext = createDate(2023, Calendar.JANUARY, 2, 10, 0);

        assertEquals("Should return start+interval when current equals start",
                expectedNext, task.nextTimeAfter(currentTime));

        currentTime = createDate(2023, Calendar.JANUARY, 5, 10, 0);
        expectedNext = createDate(2023, Calendar.JANUARY, 6, 10, 0);

        assertEquals("Should return next interval when current equals an interval point",
                expectedNext, task.nextTimeAfter(currentTime));
    }

    @Test
    void testPath_P04_RepeatedActiveBetweenTimepoints() {
        Date startTime = createDate(2023, Calendar.JANUARY, 1, 10, 0);
        Date endTime = createDate(2023, Calendar.JANUARY, 10, 10, 0);
        int interval = 24 * 60 * 60;

        task = new Task("Test Task", startTime, endTime, interval);
        task.setActive(true);

        Date currentTime = createDate(2023, Calendar.JANUARY, 2, 15, 0);

        Date expectedNext = createDate(2023, Calendar.JANUARY, 2, 10, 0);

        assertEquals("Should return timeBefore when current is between timepoints",
                expectedNext, task.nextTimeAfter(currentTime));
    }

    @Test
    void testPath_P05_LoopExitsThenNonRepeatedBeforeTask() {
        Date startTime = createDate(2023, Calendar.JANUARY, 1, 10, 0);
        Date endTime = createDate(2023, Calendar.JANUARY, 3, 10, 0);
        int interval = 24 * 60 * 60;

        task = new Task("Test Task", startTime, endTime, interval);
        task.setActive(true);

        Date currentTime = createDate(2023, Calendar.JANUARY, 2, 23, 59);

        task.setTime(createDate(2023, Calendar.JANUARY, 4, 10, 0));

        Date expectedNext = createDate(2023, Calendar.JANUARY, 4, 10, 0);

        assertEquals("Should return task time for non-repeated task after loop exit",
                expectedNext, task.nextTimeAfter(currentTime));
    }

    @Test
    void testPath_P06_RepeatedActiveOutsideRangeThenNonRepeatedBefore() {
        Date startTime = createDate(2023, Calendar.JANUARY, 1, 10, 0);
        Date endTime = createDate(2023, Calendar.JANUARY, 10, 10, 0);
        int interval = 24 * 60 * 60;

        task = new Task("Test Task", startTime, endTime, interval);
        task.setActive(true);

        task.setTime(createDate(2023, Calendar.JANUARY, 15, 10, 0));

        Date currentTime = createDate(2023, Calendar.JANUARY, 11, 10, 0);
        Date expectedNext = createDate(2023, Calendar.JANUARY, 15, 10, 0);

        assertEquals("Should return task time for non-repeated task when current is before it",
                expectedNext, task.nextTimeAfter(currentTime));
    }

    @Test
    void testPath_P07_RepeatedActiveOutsideRangeThenNonRepeatedFalse() {
        Date startTime = createDate(2023, Calendar.JANUARY, 1, 10, 0);
        Date endTime = createDate(2023, Calendar.JANUARY, 10, 10, 0);
        int interval = 24 * 60 * 60;

        task = new Task("Test Task", startTime, endTime, interval);
        task.setActive(true);

        task.setTime(createDate(2023, Calendar.JANUARY, 15, 10, 0));
        task.setActive(false);

        Date currentTime = createDate(2023, Calendar.JANUARY, 14, 10, 0);

        assertNull("Should return null for inactive non-repeated task", task.nextTimeAfter(currentTime));
    }

    @Test
    void testPath_P08_NonRepeatedActiveBeforeTask() {
        Date taskTime = createDate(2023, Calendar.JANUARY, 1, 10, 0);

        task = new Task("Test Task", taskTime);
        task.setActive(true);

        Date currentTime = createDate(2022, Calendar.DECEMBER, 31, 10, 0);

        assertEquals("Should return task time for non-repeated active task when current is before it",
                taskTime, task.nextTimeAfter(currentTime));
    }

    @Test
    void testPath_P09_NonRepeatedInactiveOrAfterTime() {
        Date taskTime = createDate(2023, Calendar.JANUARY, 1, 10, 0);

        task = new Task("Test Task", taskTime);
        task.setActive(false);

        Date currentTime = createDate(2022, Calendar.DECEMBER, 31, 10, 0);

        assertNull("Should return null for non-repeated inactive task", task.nextTimeAfter(currentTime));

        task.setActive(true);
        currentTime = createDate(2023, Calendar.JANUARY, 1, 11, 0);

        assertNull("Should return null for non-repeated active task when current is after task time",
                task.nextTimeAfter(currentTime));
    }
}