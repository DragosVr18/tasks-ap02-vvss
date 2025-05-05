package tasks.lab4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.services.TasksService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TaskIntegrationTest {

    private ArrayTaskList list;
    private TasksService service;
    private Task realTask;

    @BeforeEach
    public void setup() {
        list = new ArrayTaskList();
        service = new TasksService(list);

        Date now = new Date();
        Date later = new Date(now.getTime() + 10000);
        realTask = new Task("Real Task", now, later, 1000);
        realTask.setActive(true);
    }

    @Test
    void testAddRealTaskAndRetrieveThroughService() {
        list.add(realTask);
        assertEquals(1, service.getObservableList().size());
        assertEquals("Real Task", service.getObservableList().get(0).getTitle());
    }

    @Test
    void testFilterWithRealTask() {
        list.add(realTask);
        Date start = new Date();
        start.setTime(start.getTime() - 5000);
        Date end = new Date(start.getTime() + 20000);

        Iterable<Task> filtered = service.filterTasks(start, end);
        assertTrue(filtered.iterator().hasNext());
        assertEquals("Real Task", filtered.iterator().next().getTitle());
    }
}
