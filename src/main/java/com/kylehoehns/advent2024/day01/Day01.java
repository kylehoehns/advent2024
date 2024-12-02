package com.kylehoehns.advent2024.day01;

import com.kylehoehns.advent2024.Puzzle;
import com.kylehoehns.advent2024.PuzzleInputReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Day01 implements Puzzle {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day01.class);
    private static final String DAY = "day01";

    private final PuzzleInputReader puzzleInputReader;

    public Day01(PuzzleInputReader puzzleInputReader) {
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
        Lists lists = getLists(getLines());
        return calculateTotalDistance(lists);
    }

    int getPart2() {
        Lists lists = getLists(getLines());
        return calculateSimilarityScore(lists);
    }

    private int calculateTotalDistance(Lists lists) {
        int totalDistance = 0;
        for (int i = 0; i < lists.leftList().size(); i++) {
            totalDistance += Math.abs(lists.leftList().get(i) - lists.rightList().get(i));
        }
        return totalDistance;
    }

    private int calculateSimilarityScore(Lists lists) {
        int similarityScore = 0;
        for (int left : lists.leftList()) {
            long countInRight = lists.rightList().stream().filter(right -> right.equals(left)).count();
            similarityScore += (int) (left * countInRight);
        }
        return similarityScore;
    }

    private Lists getLists(List<String[]> lines) {
        List<Integer> leftList = lines.stream()
                .map(line -> Integer.parseInt(line[0]))
                .sorted()
                .toList();

        List<Integer> rightList = lines.stream()
                .map(line -> Integer.parseInt(line[1]))
                .sorted()
                .toList();

        return new Lists(leftList, rightList);
    }

    private List<String[]> getLines() {
        return puzzleInputReader.read(DAY).stream()
                .map(line -> line.split(" {3}"))
                .toList();
    }

    private record Lists(List<Integer> leftList, List<Integer> rightList) {
    }
}
