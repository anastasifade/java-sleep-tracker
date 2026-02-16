package ru.yandex.practicum.sleeptracker.app;

import ru.yandex.practicum.sleeptracker.analysis.*;
import ru.yandex.practicum.sleeptracker.data.*;
import ru.yandex.practicum.sleeptracker.exceptions.SleepLogException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class SleepTrackerApp {


    private static final File LOG = new File("log.txt");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    private static final SleepData sleepData = new SleepData();
    private static final SleepDataAnalyzer analyzer = new SleepDataAnalyzer();

    private static File sleepLog;
    private static PrintWriter logger;

    public static void main(String[] args) {
        try {
            sleepLog = new File(args[0]); // "src/main/resources/sleep_log.txt"
            logger = new PrintWriter(LOG);
            logState("Запуск программы.");
            loadFunctions();
            updateData(loadSleepLog());
            displayStatistics(runAnalysis());
        } catch (SleepLogException e) {
            System.out.printf("%s. Продолжение работы программы невозможно.\n", e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка при запуске программы.");
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        } finally {
            logState("Завершение работы программы.");
            if (logger != null) {
                logger.close();
            }
        }
    }

    private static void displayStatistics(List<String> statistics) {
        statistics.forEach(System.out::println);
    }

    private static List<String> runAnalysis() {
        logState("Запуск анализа данных.");
        return analyzer.performAnalysis(sleepData);
    }

    // Методы для загрузки всех необходимых для работы программы элементов

    private static List<SleepSession> loadSleepLog() throws SleepLogException {
        logState("Загрузка логов сна из файла: " + sleepLog.getName());
        List<SleepSession> sleepSessions;

        try (Stream<String> lines = Files.lines(Path.of(sleepLog.getPath()))) {
            sleepSessions = lines
                    .map(SleepTrackerApp::parseSleepSession)
                    .filter(Objects::nonNull)
                    .toList();
        } catch (IOException e) {
            logException("Ошибка загрузки лога сна.", e);
            throw new SleepLogException("Ошибка загрузки лога сна");
        }

        return sleepSessions;
    }

    private static void updateData(List<SleepSession> sessions) {
        logState("Внесение логов сна в базу данных.");
        sessions.forEach(sleepData::addSleepSession);
    }

    private static void loadFunctions() {
        logState("Загрузка функций для анализа данных.");
        // Подсчет количества сессий сна, указанных в логе
        analyzer.addFunction(new CountSessionsFunction());

        // Подсчет количества сессий сна по качеству
        analyzer.addFunction(new CountSessionsByQualityFunction(SleepQuality.GOOD));
        analyzer.addFunction(new CountSessionsByQualityFunction(SleepQuality.NORMAL));
        analyzer.addFunction(new CountSessionsByQualityFunction(SleepQuality.BAD));

        // Поиск максимальной и минимальной продолжительности сна
        analyzer.addFunction(new FindMaxDurationFunction());
        analyzer.addFunction(new FindMinDurationFunction());

        // Вычисление средней продолжительности сна
        analyzer.addFunction(new FindAverageDurationFunction());

        // Подсчет количества бессонных ночей за период наблюдения
        analyzer.addFunction(new CountSleeplessNightsFunction());

        // Анализ хронотипа пользователя
        analyzer.addFunction(new AnalyzeChronotypeFunction());
    }

    // Вспомогательные методы для обработки данных

    private static SleepSession parseSleepSession(String line) {
        String[] lineParsed = line.split(";");
        LocalDateTime start;
        LocalDateTime finish;
        SleepQuality quality;
        try {
            start = LocalDateTime.parse(lineParsed[0], formatter);
            finish = LocalDateTime.parse(lineParsed[1], formatter);
            quality = SleepQuality.valueOf(lineParsed[2]);
        } catch (DateTimeParseException e) {
            logException(("Ошибка при чтении лога сна, строка: " + line + "."), e);
            return null;
        }

        return new SleepSession(start, finish, quality);
    }

    // Методы для внесения логов

    private static void logState(String message) {
        logger.println("[INFO]: " + LocalDateTime.now() + " - " + message);
        logger.flush();
    }

    private static void logException(String message, Exception e) {
        logger.println("[ERROR]: " + LocalDateTime.now() + " - " + message);
        e.printStackTrace(logger);
        logger.flush();
    }
}