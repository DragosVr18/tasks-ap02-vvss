package tasks.lab4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.model.ArrayTaskList;
import tasks.services.TasksService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TasksServiceUnitTest {
    private TasksService service;
    private ArrayTaskList mockTaskList;

    @BeforeEach
    public void setup() {
        mockTaskList = mock(ArrayTaskList.class);
        when(mockTaskList.getAll()).thenReturn(Collections.emptyList());
        service = new TasksService(mockTaskList);
    }

    @Test
    void testFormTimeUnit() {
        assertEquals("00", service.formTimeUnit(0));
        assertEquals("05", service.formTimeUnit(5));
        assertEquals("12", service.formTimeUnit(12));
    }

    @Test
    void testParseFromStringToSeconds() {
        assertEquals(3660, service.parseFromStringToSeconds("01:01"));
        assertEquals(0, service.parseFromStringToSeconds("00:00"));
    }
}
