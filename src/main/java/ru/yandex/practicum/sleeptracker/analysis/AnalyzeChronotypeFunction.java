package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.data.Chronotype;
import ru.yandex.practicum.sleeptracker.data.SleepData;
import ru.yandex.practicum.sleeptracker.data.SleepSession;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AnalyzeChronotypeFunction implements SleepAnalysisFunction {
    @Override
    public String performAnalysis(SleepData data) {
        return "Ваш хронотип: " + getChronotype(getChronotypesCount(data.getSleepSessions()));
    }

    private String getChronotype(Map<Chronotype, Long> chronotypesCount) {

        if (chronotypesCount.isEmpty()) {
            return "[недостаточно данных]";
        }

        if (chronotypesCount.size() == 1) {
            return chronotypesCount.keySet().stream().findFirst().get().toString();
        }

        Chronotype maxChronotype = getChronotypeWithMaxCount(chronotypesCount);
        int chronotypesWithMaxCount = (int) chronotypesCount
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(chronotypesCount.get(maxChronotype)))
                .count();

        if (chronotypesWithMaxCount == 1) return maxChronotype.toString();

        return Chronotype.PIGEON.toString();
    }

    private Map<Chronotype, Long> getChronotypesCount(List<SleepSession> sessions) {
        return sessions.stream()
                .filter(this::isNighttimeSession)
                .map(session ->
                        Chronotype.getChronotype(session.getStart(), session.getFinish()))
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()));
    }

    private Chronotype getChronotypeWithMaxCount(Map<Chronotype, Long> chronotypesCount) {
        return chronotypesCount.entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .get()
                .getKey();
    }

    private boolean isNighttimeSession(SleepSession session) {
        LocalDateTime nightEnd = LocalDateTime.of(session.getFinish().toLocalDate(), LocalTime.of(6, 0));
        LocalDateTime nightStart = LocalDateTime.of(session.getFinish().toLocalDate(), LocalTime.of(0, 0));

        return session.getFinish().isAfter(nightStart) && session.getStart().isBefore(nightEnd);
    }


}