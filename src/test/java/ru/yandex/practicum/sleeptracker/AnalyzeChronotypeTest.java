package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analysis.AnalyzeChronotypeFunction;
import ru.yandex.practicum.sleeptracker.data.Chronotype;
import ru.yandex.practicum.sleeptracker.data.SleepData;
import ru.yandex.practicum.sleeptracker.data.SleepQuality;
import ru.yandex.practicum.sleeptracker.data.SleepSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AnalyzeChronotypeTest {

    private static AnalyzeChronotypeFunction function;
    private static SleepSession owlSession;
    private static SleepSession larkSession;
    private static SleepSession pigeonSession;
    private static SleepData data;
    private static String output;

    @BeforeAll
    public static void setUp() {
        function = new AnalyzeChronotypeFunction();
        LocalDate date = LocalDate.of(2026, 1, 1);
        owlSession = new SleepSession(LocalDateTime.of(date, LocalTime.of(23, 30)),
                LocalDateTime.of(date.plusDays(1), LocalTime.of(10, 0)),
                SleepQuality.GOOD);
        larkSession = new SleepSession(LocalDateTime.of(date, LocalTime.of(21, 0)),
                LocalDateTime.of(date.plusDays(1), LocalTime.of(6, 0)),
                SleepQuality.GOOD);
        pigeonSession = new SleepSession(LocalDateTime.of(date, LocalTime.of(20, 0)),
                LocalDateTime.of(date.plusDays(1), LocalTime.of(10, 0)),
                SleepQuality.GOOD);
        output = "Ваш хронотип: ";
    }

    @BeforeEach
    public void clearData() {
        data = new SleepData();
    }

    @Test
    public void noneExpectedForEmptyLog() {
        String expectedOutput = output + "[недостаточно данных]";
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }

    @Test
    public void owlExpectedForLateBedtimeLateWakeUp() {
        data.addSleepSession(owlSession);
        String expectedOutput = output + Chronotype.OWL;
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }

    @Test
    public void larkExpectedForEarlyBedtimeEarlyWakeUp() {
        data.addSleepSession(larkSession);
        String expectedOutput = output + Chronotype.LARK;
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }

    @Test
    public void pigeonExpectedForEarlyBedtimeLateWakeUp() {
        data.addSleepSession(pigeonSession);
        String expectedOutput = output + Chronotype.PIGEON;
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }

    @Test
    public void mostCommonChronotypeExpectedForMultiSessionLog() {
        data.addSleepSession(owlSession);
        data.addSleepSession(owlSession);
        data.addSleepSession(larkSession);
        data.addSleepSession(pigeonSession);

        String expectedOutput = output + Chronotype.OWL;
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }

    @Test
    public void pigeonExpectedWhenAllChronotypesHaveEqualFrequency() {
        data.addSleepSession(owlSession);
        data.addSleepSession(larkSession);
        data.addSleepSession(pigeonSession);
        String expectedOutput = output + Chronotype.PIGEON;
        Assertions.assertEquals(expectedOutput, function.performAnalysis(data));
    }
}
