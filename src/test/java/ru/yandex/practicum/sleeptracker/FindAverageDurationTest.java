package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.sleeptracker.analysis.FindAverageDurationFunction;
import ru.yandex.practicum.sleeptracker.data.SleepData;
import ru.yandex.practicum.sleeptracker.data.SleepQuality;
import ru.yandex.practicum.sleeptracker.data.SleepSession;

import java.time.Duration;
import java.time.LocalDateTime;

public class FindAverageDurationTest {

    private static FindAverageDurationFunction function;
    private static Duration firstDuration;
    private static Duration secondDuration;
    private static Duration thirdDuration;
    private static double averageDuration;
    private static SleepData data;
    private static String output;

    @BeforeAll
    public static void setUp() {
        function = new FindAverageDurationFunction();
        firstDuration = Duration.ofMinutes(45);
        secondDuration = Duration.ofMinutes(360);
        thirdDuration = Duration.ofMinutes(480);
        averageDuration = 1.0 * (firstDuration.toMinutes() + secondDuration.toMinutes() + thirdDuration.toMinutes()) / 3;
        output = "Средняя продолжительность сна (мин): ";
    }

    @BeforeEach
    public void clearData() {
        data = new SleepData();
    }

    @Test
    public void zeroExpectedForEmptyLog() {
        String expectedOutput = String.format("%s%.2f", output, 0.0);
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }

    @Test
    public void sessionDurationExpectedForSingleEntryLog() {
        data.addSleepSession(new SleepSession(LocalDateTime.now(),
                LocalDateTime.now().plus(firstDuration),
                SleepQuality.GOOD));
        String expectedOutput = String.format("%s%.2f", output, (double) firstDuration.toMinutes());
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }

    @Test
    public void averageDurationExpectedForMultiEntryLog() {
        data.addSleepSession(new SleepSession(LocalDateTime.now(),
                LocalDateTime.now().plus(firstDuration),
                SleepQuality.GOOD));
        data.addSleepSession(new SleepSession(LocalDateTime.now(),
                LocalDateTime.now().plus(secondDuration),
                SleepQuality.GOOD));
        data.addSleepSession(new SleepSession(LocalDateTime.now(),
                LocalDateTime.now().plus(thirdDuration),
                SleepQuality.GOOD));
        String expectedOutput = String.format("%s%.2f", output, averageDuration);
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }

}
