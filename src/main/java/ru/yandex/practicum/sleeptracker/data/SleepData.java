package ru.yandex.practicum.sleeptracker.data;

// Класс для хранения статистики

import java.util.ArrayList;
import java.util.List;

public class SleepData {

    private final List<SleepSession> sleepSessions;

    public SleepData() {
        sleepSessions = new ArrayList<>();
    }

    public void addSleepSession(SleepSession session) {
        sleepSessions.add(session);
    }

    public List<SleepSession> getSleepSessions() {
        return List.copyOf(sleepSessions);
    }


}
