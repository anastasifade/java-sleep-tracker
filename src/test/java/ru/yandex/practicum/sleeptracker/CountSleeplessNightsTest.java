package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analysis.CountSleeplessNightsFunction;
import ru.yandex.practicum.sleeptracker.data.SleepData;
import ru.yandex.practicum.sleeptracker.data.SleepQuality;
import ru.yandex.practicum.sleeptracker.data.SleepSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CountSleeplessNightsTest {

    private static CountSleeplessNightsFunction function;

    private static LocalDate firstDay;
    private static LocalTime earlyMorning;
    private static LocalTime lateMorning;
    private static LocalTime noon;
    private static LocalTime earlyEvening;
    private static LocalTime lateEvening;

    private static SleepData data;
    private static String output;

    @BeforeAll
    public static void setUp() {
        function = new CountSleeplessNightsFunction();

        firstDay = LocalDate.of(2026, 1, 1);
        earlyMorning = LocalTime.of(5, 0);
        lateMorning = LocalTime.of(8, 0);
        noon = LocalTime.of(12, 0);
        earlyEvening = LocalTime.of(21, 0);
        lateEvening = LocalTime.of(23, 0);

        output = "Количество бессонных ночей: ";
    }

    @BeforeEach
    public void clearData() {
        data = new SleepData();
    }

    @Test
    public void zeroExpectedForEmptyLog() {
        String expectedOutput = output + 0;
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }

    @Test
    public void oneExpectedForSingleSessionStartingBetween_0600_and_1200() {
        data.addSleepSession(new SleepSession(LocalDateTime.of(firstDay, lateMorning),
                LocalDateTime.of(firstDay, noon),
                SleepQuality.GOOD));
        String expectedOutput = output + 1;
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }

    @Test
    public void zeroExpectedForSingleSessionStartingBefore_0600() {
        data.addSleepSession(new SleepSession(LocalDateTime.of(firstDay, earlyMorning),
                LocalDateTime.of(firstDay, lateMorning),
                SleepQuality.GOOD));
        String expectedOutput = output + 0;
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }

    @Test
    public void zeroExpectedForSingleSessionBetween_1200_and_2400() {
        data.addSleepSession(new SleepSession(LocalDateTime.of(firstDay, earlyEvening),
                LocalDateTime.of(firstDay, lateEvening),
                SleepQuality.GOOD));
        String expectedOutput = output + 0;
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }

    @Test
    public void oneExpectedForOneSkippedDayInLog() {
        // 01.01.2026 05:00 - 01.01.2026 08:00
        data.addSleepSession(new SleepSession(LocalDateTime.of(firstDay, earlyMorning),
                LocalDateTime.of(firstDay, lateMorning),
                SleepQuality.GOOD));
        // 02.01.2026 23:00 - 03.01.2026 05:00
        data.addSleepSession(new SleepSession(LocalDateTime.of(firstDay.plusDays(1), lateEvening),
                LocalDateTime.of(firstDay.plusDays(2), earlyMorning),
                SleepQuality.GOOD));
        String expectedOutput = output + 1;
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }

    @Test
    public void zeroExpectedForSingleMultiDaySession() {
        data.addSleepSession(new SleepSession(LocalDateTime.of(firstDay, earlyMorning),
                LocalDateTime.of(firstDay.plusDays(5), earlyMorning),
                SleepQuality.BAD));
        String expectedOutput = output + 0;
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }

    @Test
    public void zeroExpectedForConsecutiveMultiDaySessions() {
        SleepSession first = new SleepSession(LocalDateTime.of(firstDay, earlyMorning),
                LocalDateTime.of(firstDay.plusDays(5), earlyMorning),
                SleepQuality.BAD);
        SleepSession second = new SleepSession(first.getFinish(), first.getFinish().plusDays(5), SleepQuality.BAD);

        data.addSleepSession(first);
        data.addSleepSession(second);

        String expectedOutput = output + 0;
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }

}
