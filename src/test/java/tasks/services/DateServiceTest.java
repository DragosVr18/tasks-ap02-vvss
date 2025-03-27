package tasks.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import tasks.model.ArrayTaskList;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DateService tests")
@Tag("DateService")
class DateServiceTest {
    private final DateService dateService = new DateService(new TasksService(new ArrayTaskList()));

    private void testGetDateMergedWithTime(String testTime, boolean isValid) {
        Date baseDate = new GregorianCalendar(2023, Calendar.DECEMBER, 12).getTime();

        if (!isValid) {
            assertThrows(IllegalArgumentException.class, () -> dateService.getDateMergedWithTime(testTime, baseDate));
        } else {
            Date result = dateService.getDateMergedWithTime(testTime, baseDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(result);

            String[] parts = testTime.split(":");
            int expectedHour = Integer.parseInt(parts[0]);
            int expectedMinute = Integer.parseInt(parts[1]);

            assertEquals(expectedHour, calendar.get(Calendar.HOUR_OF_DAY));
            assertEquals(expectedMinute, calendar.get(Calendar.MINUTE));
            assertEquals(12, calendar.get(Calendar.DAY_OF_MONTH));
            assertEquals(Calendar.DECEMBER, calendar.get(Calendar.MONTH));
            assertEquals(2023, calendar.get(Calendar.YEAR));
        }
    }

    @DisplayName("Equivalence class test cases for getDateMergedWithTime")
    @ParameterizedTest
    @CsvSource({
            "10:40, true",
            "10:78, false",
            "10:-5, false",
            "57:40, false",
            "57:78, false",
            "57:-5, false",
            "-5:40, false",
            "-5:78, false",
            "-5:-5, false",
            "smth_no, false"
    })
    void testGetDateMergedWithTimeEC(String testTime, boolean isValid) {
        testGetDateMergedWithTime(testTime, isValid);
    }

    @DisplayName("BVA test cases for getDateMergedWithTime")
    @ParameterizedTest
    @MethodSource("boundaryValueTestCases")
    void getDateMergedWithTimeValidBVA(String testTime, boolean isValid) {
        testGetDateMergedWithTime(testTime, isValid);
    }

    private static Stream<Arguments> boundaryValueTestCases() {
        return Stream.of(
                Arguments.of("00:00", true),
                Arguments.of("00:-1", false),
                Arguments.of("00:01", true),
                Arguments.of("00:59", true),
                Arguments.of("00:58", true),
                Arguments.of("00:60", false),
                Arguments.of("-1:00", false),
                Arguments.of("-1:-1", false),
                Arguments.of("-1:01", false),
                Arguments.of("-1:59", false),
                Arguments.of("-1:58", false),
                Arguments.of("-1:60", false),
                Arguments.of("01:00", true),
                Arguments.of("01:-1", false),
                Arguments.of("01:01", true),
                Arguments.of("01:59", true),
                Arguments.of("01:58", true),
                Arguments.of("01:60", false),
                Arguments.of("23:00", true),
                Arguments.of("23:-1", false),
                Arguments.of("23:01", true),
                Arguments.of("23:59", true),
                Arguments.of("23:58", true),
                Arguments.of("23:60", false),
                Arguments.of("22:00", true),
                Arguments.of("22:-1", false),
                Arguments.of("22:01", true),
                Arguments.of("22:59", true),
                Arguments.of("22:58", true),
                Arguments.of("22:60", false),
                Arguments.of("24:00", false),
                Arguments.of("24:-1", false),
                Arguments.of("24:01", false),
                Arguments.of("24:59", false),
                Arguments.of("24:58", false),
                Arguments.of("24:60", false)
        );
    }
}
