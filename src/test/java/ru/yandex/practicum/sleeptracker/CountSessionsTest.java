package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analysis.CountSessionsFunction;
import ru.yandex.practicum.sleeptracker.data.SleepData;
import ru.yandex.practicum.sleeptracker.data.SleepQuality;
import ru.yandex.practicum.sleeptracker.data.SleepSession;

import java.time.LocalDateTime;

public class CountSessionsTest {

    private static CountSessionsFunction function;
    private static SleepData data;
    private static String output;


    @BeforeAll
    public static void setUp() {
        function = new CountSessionsFunction();
        output = "Количество записанных сессий сна: ";
    }

    @BeforeEach
    public void clearData() {
        data = new SleepData();
    }

    @Test
    public void zeroExpectedForEmptyLogs() {
        String expectedOutput = output + 0;
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }

    @Test
    public void numExpectedForNumLogs() {
        data.addSleepSession(new SleepSession(LocalDateTime.now(),
                LocalDateTime.now().plusHours(6),
                SleepQuality.GOOD));
        data.addSleepSession(new SleepSession(LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                SleepQuality.BAD));

        String expectedOutput = output + data.getSleepSessions().size();
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }
}
