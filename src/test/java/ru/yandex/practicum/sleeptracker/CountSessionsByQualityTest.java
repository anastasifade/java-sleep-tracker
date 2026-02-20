package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analysis.CountSessionsByQualityFunction;
import ru.yandex.practicum.sleeptracker.data.SleepData;
import ru.yandex.practicum.sleeptracker.data.SleepQuality;
import ru.yandex.practicum.sleeptracker.data.SleepSession;

import java.time.LocalDateTime;

public class CountSessionsByQualityTest {

    private static CountSessionsByQualityFunction function;
    private static SleepQuality testingQuality;
    private static SleepQuality differentQuality;
    private static SleepData data;
    private static String output;

    @BeforeAll
    public static void setUp() {
        testingQuality = SleepQuality.BAD;
        differentQuality = SleepQuality.GOOD;
        function = new CountSessionsByQualityFunction(testingQuality);
        output = "Количество сессий сна плохого качества: ";
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
    public void zeroExpectedForNoMatchingQualitySessionsInLog() {
        data.addSleepSession(new SleepSession(LocalDateTime.now(), LocalDateTime.now(), differentQuality));
        String expectedOutput = output + 0;
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }

    @Test
    public void numExpectedForNumMatchingQualitySessionsInLog() {
        data.addSleepSession(new SleepSession(LocalDateTime.now(), LocalDateTime.now(), testingQuality));
        String expectedOutput = output + 1;
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }
}
