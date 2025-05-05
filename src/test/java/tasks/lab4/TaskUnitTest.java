package tasks.lab4;

import org.junit.jupiter.api.Test;
import tasks.model.Task;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TaskUnitTest {

    @Test
    public void testNonRepeatedTaskNextTimeAfter() {
        Date now = new Date();
        Date future = new Date(now.getTime() + 60000); // +1 minute
        Task task = new Task("Test Task", future);
        task.setActive(true);

        assertEquals(future, task.nextTimeAfter(now));
        assertNull(task.nextTimeAfter(future));
    }

    @Test
    public void testRepeatedTaskNextTimeAfter() {
        Date start = new Date();
        Date end = new Date(start.getTime() + 60000 * 5); // +5 minutes
        Task task = new Task("Repeated Task", start, end, 60); // 60 sec interval
        task.setActive(true);

        Date after1min = new Date(start.getTime() + 60000);
        assertEquals(after1min, task.nextTimeAfter(new Date(start.getTime())));
    }
}
