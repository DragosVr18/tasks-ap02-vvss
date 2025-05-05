package tasks.lab4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.services.TasksService;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TasksServiceArrayTaskListIntegrationTest {

    private ArrayTaskList list;
    private TasksService service;

    @BeforeEach
    public void setup() {
        list = new ArrayTaskList();
        service = new TasksService(list);
    }

    @Test
    void testGetObservableListWithMockTask() {
        Task mockTask = mock(Task.class);
        when(mockTask.getTitle()).thenReturn("Mocked Task");

        list.add(mockTask);
        List<Task> observable = service.getObservableList();

        assertEquals(1, observable.size());
        assertEquals("Mocked Task", observable.get(0).getTitle());
    }

    @Test
    void testFilterTasksWithMockedNextTimeAfter() {
        Task mockTask = mock(Task.class);
        when(mockTask.getTitle()).thenReturn("Task 1");
        when(mockTask.nextTimeAfter(any(Date.class))).thenReturn(new Date());

        list.add(mockTask);

        Date now = new Date();
        Date future = new Date(now.getTime() + 10000);

        Iterable<Task> filtered = service.filterTasks(now, future);
        assertTrue(filtered.iterator().hasNext());
        assertEquals("Task 1", filtered.iterator().next().getTitle());

        verify(mockTask).nextTimeAfter(any(Date.class));
    }
}