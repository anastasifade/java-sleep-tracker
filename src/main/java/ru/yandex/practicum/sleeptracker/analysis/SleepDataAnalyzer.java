package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.data.SleepData;

import java.util.*;

public class SleepDataAnalyzer {

    private final List<SleepAnalysisFunction> functions;

    public SleepDataAnalyzer() {
        this.functions = new ArrayList<>();
    }

    public void addFunction(SleepAnalysisFunction function) {
        functions.add(function);
    }

    public List<String> performAnalysis(SleepData data) {
        return functions
                .stream()
                .map(function -> function.performAnalysis(data))
                .toList();
    }
}
