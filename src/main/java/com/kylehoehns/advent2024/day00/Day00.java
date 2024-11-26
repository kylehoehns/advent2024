package com.kylehoehns.advent2024.day00;

import com.kylehoehns.advent2024.Puzzle;
import com.kylehoehns.advent2024.PuzzleInputReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;


@Component
public class Day00 implements Puzzle {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day00.class);

    private static final String DAY = "day00";

    private final PuzzleInputReader puzzleInputReader;

    public Day00(PuzzleInputReader puzzleInputReader) {
        this.puzzleInputReader = puzzleInputReader;
    }

    int getPart1() {
        var lines = puzzleInputReader.readInts(DAY);
        var seen = new HashSet<>();
        for (int number : lines) {
            int complement = 2020 - number;
            if (seen.contains(complement)) {
                return number * complement;
            }
            seen.add(number);
        }
        return -1;
    }

    int getPart2() {
        var lines = puzzleInputReader.readInts(DAY);

        for (int i = 0; i < lines.size(); i++) {
            for (int j = i + 1; j < lines.size(); j++) {
                for (int k = j + 1; k < lines.size(); k++) {
                    if (lines.get(i) + lines.get(j) + lines.get(k) == 2020) {
                        return lines.get(i) * lines.get(j) * lines.get(k);
                    }
                }
            }
        }
        return -1;
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
}
