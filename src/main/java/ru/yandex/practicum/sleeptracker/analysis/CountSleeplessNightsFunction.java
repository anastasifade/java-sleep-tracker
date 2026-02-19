package ru.yandex.practicum.sleeptracker.analysis;

import ru.yandex.practicum.sleeptracker.data.SleepData;
import ru.yandex.practicum.sleeptracker.data.SleepSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CountSleeplessNightsFunction implements SleepAnalysisFunction {

    private List<SleepSession> sessions;

    @Override
    public String performAnalysis(SleepData data) {
        sessions = data.getSleepSessions();

        if (sessions.isEmpty()) return "Количество бессонных ночей: 0";

        LocalDateTime firstSessionStart = sessions.getFirst().getStart();
        LocalDate firstDayToCount = firstSessionStart.toLocalDate();
        if (firstSessionStart.toLocalTime().isAfter(LocalTime.of(12, 0))) {

            // в случае, если в логе содержится единственная запись сессии сна,
            // которую можно отнести к дневному сну - данных для продолжения анализа недостаточно
            if (sessions.size() == 1 && sessions.getFirst().getFinish().toLocalDate().equals(firstDayToCount)) {
                return "Количество бессонных ночей: 0";
            }

            firstDayToCount = firstDayToCount.plusDays(1);
        }

        LocalDate lastDayToCount = sessions.getLast().getFinish().toLocalDate();

        return "Количество бессонных ночей: " + Stream.iterate(firstDayToCount,
                        date -> !date.isAfter(lastDayToCount),
                        date -> date.plusDays(1))
                .filter(this::hasSleeplessNight)
                .count();
    }

    private boolean hasSleeplessNight(LocalDate day) {

        if (getSessionForDate(day).isEmpty()) return true;
        SleepSession session = getSessionForDate(day).get();
        return session.getStart().isAfter(LocalDateTime.of(session.getFinish().toLocalDate(),
                LocalTime.of(6, 0)));
    }

    private Optional<SleepSession> getSessionForDate(LocalDate date) {
        return sessions.stream()
                .filter(session -> session.getFinish().toLocalDate().isEqual(date))
                .findFirst();
    }
}
