package ru.yandex.practicum.sleeptracker.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum Chronotype {
    OWL("сова"),
    LARK("жаворонок"),
    PIGEON("голубь");

    private static final LocalTime OWL_GO_TO_BED_AFTER = LocalTime.of(23, 0);
    private static final LocalTime OWL_WAKE_UP_AFTER = LocalTime.of(9, 0);

    private static final LocalTime LARK_GO_TO_BED_BEFORE = LocalTime.of(22, 0);
    private static final LocalTime LARK_WAKE_UP_BEFORE = LocalTime.of(7, 0);

    private final String type;

    Chronotype(String type) {
        this.type = type;
    }

    public static Chronotype getChronotype(LocalDateTime wentToBed, LocalDateTime wokeUp) {
        LocalDate dateWentToBed = wentToBed.toLocalDate();
        LocalDate dateWokeUp = wokeUp.toLocalDate();

        // В случае, если отход ко сну был больше полуночи, сравнение проводится относительно времени
        // предыдущего календарного дня.
        if (wentToBed.toLocalTime().isBefore(LocalTime.of(6, 0))) {
            dateWentToBed = dateWentToBed.minusDays(1);
        }

        LocalDateTime owlBedtime = LocalDateTime.of(dateWentToBed, OWL_GO_TO_BED_AFTER);
        LocalDateTime owlWakeUpTime = LocalDateTime.of(dateWokeUp, OWL_WAKE_UP_AFTER);
        if (wentToBed.isAfter(owlBedtime) && wokeUp.isAfter(owlWakeUpTime)) {
            return OWL;
        }

        LocalDateTime larkBedtime = LocalDateTime.of(dateWentToBed, LARK_GO_TO_BED_BEFORE);
        LocalDateTime larkWakeUpTime = LocalDateTime.of(dateWokeUp, LARK_WAKE_UP_BEFORE);
        if (wentToBed.isBefore(larkBedtime) && wokeUp.isBefore(larkWakeUpTime)) {
            return LARK;
        }

        return PIGEON;
    }

    @Override
    public String toString() {
        return this.type;
    }

}
