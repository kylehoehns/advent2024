package com.kylehoehns.advent2024.day07;

import com.kylehoehns.advent2024.Puzzle;
import com.kylehoehns.advent2024.PuzzleInputReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.BiFunction;

@Component
public class Day07 implements Puzzle {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day07.class);
    private static final String DAY = "day07";

    private static final List<BiFunction<Long, Long, Long>> PART_1_OPERATORS = List.of(
            Long::sum,
            (a, b) -> a * b
    );

    private static final List<BiFunction<Long, Long, Long>> PART_2_OPERATORS = List.of(
            Long::sum,
            (a, b) -> a * b,
            (a, b) -> Long.parseLong(a.toString() + b)
    );

    private final PuzzleInputReader puzzleInputReader;

    public Day07(PuzzleInputReader puzzleInputReader) {
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

    long getPart1() {
        return solve(PART_1_OPERATORS);
    }

    long getPart2() {
        return solve(PART_2_OPERATORS);
    }

    record Equation(Long test, List<Long> numbers) {
        public boolean isSolvable(List<BiFunction<Long, Long, Long>> operators) {
            if (numbers.isEmpty()) return false;
            return solveRecursive(numbers.subList(1, numbers.size()), List.of(numbers.getFirst()), operators);
        }

        private boolean solveRecursive(List<Long> remaining, List<Long> results, List<BiFunction<Long, Long, Long>> operators) {
            if (remaining.isEmpty()) {
                return results.contains(test);
            }

            Long current = remaining.getFirst();

            List<Long> newResults = new ArrayList<>();
            for (Long result : results) {
                operators.forEach(op -> newResults.add(op.apply(result, current)));
            }

            return solveRecursive(remaining.subList(1, remaining.size()), newResults, operators);
        }
    }

    private long solve(List<BiFunction<Long, Long, Long>> operators) {
        return parseEquations().stream()
                .filter(eq -> eq.isSolvable(operators))
                .mapToLong(Equation::test)
                .sum();
    }

    private List<Equation> parseEquations() {
        return puzzleInputReader.read(DAY).stream()
                .map(line -> {
                    var parts = line.split(":");
                    var test = Long.parseLong(parts[0]);
                    var numbers = Arrays.stream(parts[1].trim().split(" "))
                            .map(Long::parseLong)
                            .toList();
                    return new Equation(test, numbers);
                })
                .toList();
    }
}
