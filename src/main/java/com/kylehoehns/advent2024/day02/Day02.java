package com.kylehoehns.advent2024.day02;

import com.kylehoehns.advent2024.Puzzle;
import com.kylehoehns.advent2024.PuzzleInputReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class Day02 implements Puzzle {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day02.class);
    private static final String DAY = "day02";

    private final PuzzleInputReader puzzleInputReader;

    public Day02(PuzzleInputReader puzzleInputReader) {
        this.puzzleInputReader = puzzleInputReader;
    }

    @Override
    public String getDay() {
        return DAY;
    }

    @Override
    public void run() {
        LOGGER.info("Part 1: {}", getPart1());
        LOGGER.info("Part 2: {}", getPart2());
    }

    record Report(List<Integer> levels) {

        boolean isSafe() {
            if (levels.isEmpty() || levels.size() < 2) {
                return false;
            }

            String initialDirection = levels.get(1) > levels.getFirst() ? "INCREASE" : "DECREASE";

            for (int i = 1; i < levels.size(); i++) {
                int currentLevel = levels.get(i);
                int previousLevel = levels.get(i - 1);
                int difference = Math.abs(previousLevel - currentLevel);

                if (difference == 0 || difference > 3) {
                    return false;
                }

                String currentDirection = currentLevel > previousLevel ? "INCREASE" : "DECREASE";
                if (!initialDirection.equals(currentDirection)) {
                    return false;
                }
            }

            return true;
        }
    }

    long getPart1() {
        return parseReports().stream()
                .filter(Report::isSafe)
                .count();
    }

    long getPart2() {
        List<Report> reports = parseReports();
        int safeReportCount = 0;

        for (Report report : reports) {
            if (report.isSafe()) {
                safeReportCount++;
            } else {
                if (canBeMadeSafe(report)) {
                    safeReportCount++;
                }
            }
        }

        return safeReportCount;
    }

    private boolean canBeMadeSafe(Report report) {
        for (int indexToSkip = 0; indexToSkip < report.levels().size(); indexToSkip++) {
            int finalIndexToSkip = indexToSkip;
            List<Integer> modifiedLevels = IntStream.range(0, report.levels().size())
                    .filter(i -> i != finalIndexToSkip)
                    .mapToObj(report.levels()::get)
                    .collect(Collectors.toList());

            if (new Report(modifiedLevels).isSafe()) {
                return true;
            }
        }
        return false;
    }

    private List<Report> parseReports() {
        return puzzleInputReader.read(DAY).stream()
                .map(line -> new Report(
                        Arrays.stream(line.split(" "))
                                .map(Integer::parseInt)
                                .toList()
                ))
                .toList();
    }
}
