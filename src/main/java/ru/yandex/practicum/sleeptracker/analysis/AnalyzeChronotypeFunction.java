package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.data.Chronotype;
import ru.yandex.practicum.sleeptracker.data.SleepData;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AnalyzeChronotypeFunction implements SleepAnalysisFunction {
    @Override
    public String performAnalysis(SleepData data) {
        return "Ваш хронотип: " +
                data.getSleepSessions()
                        .stream()
                        .map(session ->
                                Chronotype.getChronotype(session.getStart(), session.getFinish()))
                        .collect(Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting())).entrySet()
                        .stream()
                        .max(Comparator.comparingLong(Map.Entry::getValue))
                        .map(entry -> entry.getKey().toString())
                        .orElse("[недостаточно данных]");
    }
}