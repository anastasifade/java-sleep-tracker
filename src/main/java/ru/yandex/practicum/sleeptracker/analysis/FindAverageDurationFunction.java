package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.data.SleepData;

public class FindAverageDurationFunction implements SleepAnalysisFunction {

    @Override
    public String performAnalysis(SleepData data) {
        return String.format("Средняя продолжительность сна (мин): %.2f", getAverage(data));
    }

    public double getAverage(SleepData data) {
        return data.getSleepSessions()
                .stream()
                .mapToDouble(session -> session.getDuration().toMinutes())
                .average()
                .orElse(0.0);
    }


}
