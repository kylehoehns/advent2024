package com.kylehoehns.advent2024.day03;

import com.kylehoehns.advent2024.Puzzle;
import com.kylehoehns.advent2024.PuzzleInputReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Day03 implements Puzzle {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day03.class);
    private static final String DAY = "day03";
    private static final Pattern MUL_PATTERN = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
    private static final Pattern DO_DONT_PATTERN = Pattern.compile("(?:do\\(\\)|don't\\(\\))(.*?)(?=do\\(\\)|don't\\(\\)|$)");

    private final PuzzleInputReader puzzleInputReader;

    public Day03(PuzzleInputReader puzzleInputReader) {
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

    int getPart1() {
        String memory = String.join("", puzzleInputReader.read(DAY));
        return getTotalMulValue(memory);
    }

    int getPart2() {
        String memory = String.join("", puzzleInputReader.read(DAY));
        Matcher matcher = DO_DONT_PATTERN.matcher(memory);
        StringBuilder enabledMemory = new StringBuilder();

        // Add the first chunk before the first "don't"
        String[] splits = memory.split("don't\\(\\)", 2);
        if (splits.length > 0) {
            enabledMemory.append(splits[0]);
        }

        // Append enabled sections
        while (matcher.find()) {
            if (matcher.group().startsWith("do()")) {
                enabledMemory.append(matcher.group());
            }
        }

        return getTotalMulValue(enabledMemory.toString());
    }

    private static int getTotalMulValue(String memory) {
        Matcher matcher = MUL_PATTERN.matcher(memory);
        int total = 0;
        while (matcher.find()) {
            total += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
        }
        return total;
    }
}
