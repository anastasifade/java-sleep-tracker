package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.data.SleepData;

import java.util.Comparator;

public class FindMinDurationFunction implements SleepAnalysisFunction {
    @Override
    public String performAnalysis(SleepData data) {
        return "Минимальная продолжительность сна (мин): " +
                data.getSleepSessions()
                        .stream()
                        .min(Comparator.comparingInt(o -> (int) o.getDuration().toMinutes()))
                        .map(session ->
                                (int) session.getDuration().toMinutes())
                        .orElse(0);
    }
}
