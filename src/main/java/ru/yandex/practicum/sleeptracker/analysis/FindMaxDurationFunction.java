package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.data.SleepData;

import java.util.Comparator;

public class FindMaxDurationFunction implements SleepAnalysisFunction {
    @Override
    public String performAnalysis(SleepData data) {
        return "Максимальная продолжительность сна (мин): " +
                data.getSleepSessions()
                        .stream()
                        .max(Comparator.comparingInt(o -> (int) o.getDuration().toMinutes()))
                        .map(session ->
                                (int) session.getDuration().toMinutes())
                        .orElse(0);
    }
}
