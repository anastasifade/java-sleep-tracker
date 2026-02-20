package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analysis.FindMaxDurationFunction;
import ru.yandex.practicum.sleeptracker.data.SleepData;
import ru.yandex.practicum.sleeptracker.data.SleepQuality;
import ru.yandex.practicum.sleeptracker.data.SleepSession;

import java.time.Duration;
import java.time.LocalDateTime;

public class FindMaxDurationTest {

    private static FindMaxDurationFunction function;
    private static Duration minDuration;
    private static Duration midDuration;
    private static Duration maxDuration;
    private static SleepData data;
    private static String output;

    @BeforeAll
    public static void setUp() {
        function = new FindMaxDurationFunction();
        minDuration = Duration.ofMinutes(45);
        midDuration = Duration.ofMinutes(360);
        maxDuration = Duration.ofMinutes(480);
        output = "Максимальная продолжительность сна (мин): ";
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
    public void sessionDurationExpectedForSingleEntryLog() {
        data.addSleepSession(new SleepSession(LocalDateTime.now(),
                LocalDateTime.now().plus(minDuration),
                SleepQuality.GOOD));
        String expectedOutput = output + minDuration.toMinutes();
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }

    @Test
    public void maxDurationExpectedForMultiSessionLog() {
        data.addSleepSession(new SleepSession(LocalDateTime.now(),
                LocalDateTime.now().plus(minDuration),
                SleepQuality.GOOD));
        data.addSleepSession(new SleepSession(LocalDateTime.now(),
                LocalDateTime.now().plus(maxDuration),
                SleepQuality.GOOD));
        data.addSleepSession(new SleepSession(LocalDateTime.now(),
                LocalDateTime.now().plus(midDuration),
                SleepQuality.GOOD));
        String expectedOutput = output + maxDuration.toMinutes();
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }
}
