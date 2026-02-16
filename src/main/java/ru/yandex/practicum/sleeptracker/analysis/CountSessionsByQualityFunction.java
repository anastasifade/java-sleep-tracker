package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.data.SleepData;
import ru.yandex.practicum.sleeptracker.data.SleepQuality;

public class CountSessionsByQualityFunction implements SleepAnalysisFunction {

    private final SleepQuality quality;

    public CountSessionsByQualityFunction(SleepQuality quality) {
        this.quality = quality;
    }

    @Override
    public String performAnalysis(SleepData data) {
        return "Количество сессий сна " + quality.getGenitive() + " качества: " +
                data.getSleepSessions()
                        .stream()
                        .filter(session -> session.getQuality().equals(quality))
                        .count();
    }
}