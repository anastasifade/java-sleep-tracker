package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.data.SleepData;

public class CountSessionsFunction implements SleepAnalysisFunction {

    @Override
    public String performAnalysis(SleepData data) {
        return "Количество записанных сессий сна: " + data.getSleepSessions().size();
    }
}
