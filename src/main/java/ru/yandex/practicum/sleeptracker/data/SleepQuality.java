package ru.yandex.practicum.sleeptracker.data;

public enum SleepQuality {
    GOOD("хорошего"),
    NORMAL("среднего"),
    BAD("плохого");

    private final String genitive; // обозначение качества сна в родительном падеже - для вывода статистики

    SleepQuality(String genitive) {
        this.genitive = genitive;
    }

    public String getGenitive() {
        return this.genitive;
    }
}
