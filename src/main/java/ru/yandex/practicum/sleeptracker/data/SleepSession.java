package ru.yandex.practicum.sleeptracker.data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SleepSession {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    private final LocalDateTime start;
    private final LocalDateTime finish;
    private final SleepQuality quality;
    private final Duration duration;

    public SleepSession(LocalDateTime start, LocalDateTime finish, SleepQuality quality) {
        this.start = start;
        this.finish = finish;
        this.quality = quality;
        this.duration = Duration.between(start, finish);
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getFinish() {
        return this.finish;
    }

    public SleepQuality getQuality() {
        return this.quality;
    }

    public Duration getDuration() {
        return this.duration;
    }

    @Override
    public String toString() {
        return "Начало: " + this.start.format(FORMATTER) + ". Конец: " + this.finish.format(FORMATTER) +
                ". Качество: " + this.quality + ".";
    }

}
