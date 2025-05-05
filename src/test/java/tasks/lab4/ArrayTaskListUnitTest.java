package tasks.lab4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.model.ArrayTaskList;
import tasks.model.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ArrayTaskListUnitTest {
    private ArrayTaskList list;

    @BeforeEach
    public void setUp() {
        list = new ArrayTaskList();
    }

    @Test
    void testAddAndContainsMockTask() {
        Task mockTask = mock(Task.class);
        when(mockTask.getTitle()).thenReturn("Mock Task");

        list.add(mockTask);
        List<Task> allTasks = list.getAll();

        assertEquals(1, allTasks.size());
        assertEquals("Mock Task", allTasks.get(0).getTitle());
    }

    @Test
    void testRemoveMockTask() {
        Task mockTask = mock(Task.class);
        list.add(mockTask);

        boolean removed = list.remove(mockTask);
        assertTrue(removed);
        assertEquals(0, list.getAll().size());
    }
}
