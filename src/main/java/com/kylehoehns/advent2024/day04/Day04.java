package com.kylehoehns.advent2024.day04;

import com.kylehoehns.advent2024.Puzzle;
import com.kylehoehns.advent2024.PuzzleInputReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class Day04 implements Puzzle {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day04.class);
    private static final String DAY = "day04";

    private final PuzzleInputReader puzzleInputReader;

    public Day04(PuzzleInputReader puzzleInputReader) {
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
        List<List<String>> grid = parseInputToGrid();

        int matches = 0;
        for (int row = 0; row < grid.size(); row++) {
            for (int col = 0; col < grid.get(row).size(); col++) {
                var value = grid.get(row).get(col);
                if ("X".equals(value)) {
                    String first, second, third;
                    // NW
                    first = grid.get(row - 1).get(col - 1);
                    second = grid.get(row - 2).get(col - 2);
                    third = grid.get(row - 3).get(col - 3);
                    if ("M".equals(first) && "A".equals(second) && "S".equals(third)) {
                        matches++;
                    }
                    // N
                    first = grid.get(row - 1).get(col);
                    second = grid.get(row - 2).get(col);
                    third = grid.get(row - 3).get(col);
                    if ("M".equals(first) && "A".equals(second) && "S".equals(third)) {
                        matches++;
                    }
                    // NE
                    first = grid.get(row - 1).get(col + 1);
                    second = grid.get(row - 2).get(col + 2);
                    third = grid.get(row - 3).get(col + 3);
                    if ("M".equals(first) && "A".equals(second) && "S".equals(third)) {
                        matches++;
                    }
                    // W
                    first = grid.get(row).get(col - 1);
                    second = grid.get(row).get(col - 2);
                    third = grid.get(row).get(col - 3);
                    if ("M".equals(first) && "A".equals(second) && "S".equals(third)) {
                        matches++;
                    }
                    // E
                    first = grid.get(row).get(col + 1);
                    second = grid.get(row).get(col + 2);
                    third = grid.get(row).get(col + 3);
                    if ("M".equals(first) && "A".equals(second) && "S".equals(third)) {
                        matches++;
                    }
                    // SW
                    first = grid.get(row + 1).get(col - 1);
                    second = grid.get(row + 2).get(col - 2);
                    third = grid.get(row + 3).get(col - 3);
                    if ("M".equals(first) && "A".equals(second) && "S".equals(third)) {
                        matches++;
                    }
                    // S
                    first = grid.get(row + 1).get(col);
                    second = grid.get(row + 2).get(col);
                    third = grid.get(row + 3).get(col);
                    if ("M".equals(first) && "A".equals(second) && "S".equals(third)) {
                        matches++;
                    }
                    // SE
                    first = grid.get(row + 1).get(col + 1);
                    second = grid.get(row + 2).get(col + 2);
                    third = grid.get(row + 3).get(col + 3);
                    if ("M".equals(first) && "A".equals(second) && "S".equals(third)) {
                        matches++;
                    }
                }
            }
        }

        return matches;
    }

    int getPart2() {
        List<List<String>> grid = parseInputToGrid();

        int matches = 0;
        for (int row = 0; row < grid.size(); row++) {
            for (int col = 0; col < grid.get(row).size(); col++) {
                var value = grid.get(row).get(col);
                if ("A".equals(value)) {
                    String nw = grid.get(row - 1).get(col - 1);
                    String se = grid.get(row + 1).get(col + 1);
                    String sw = grid.get(row + 1).get(col - 1);
                    String ne = grid.get(row - 1).get(col + 1);
                    boolean backSlashMatch = ("M".equals(nw) && "S".equals(se)) || ("S".equals(nw) && "M".equals(se));
                    boolean forwardSlashMatch = ("M".equals(sw) && "S".equals(ne)) || ("S".equals(sw) && "M".equals(ne));
                    if (backSlashMatch && forwardSlashMatch) {
                        matches++;
                    }
                }
            }
        }
        return matches;
    }

    private List<List<String>> parseInputToGrid() {
        var lines = puzzleInputReader.read(DAY);
        var tokenizedLines = new ArrayList<List<String>>();
        for (int i = 0; i < lines.size(); i++) {

            // go ahead and add 3 padding lines above and below the input, as well as 3 padding E and W of input
            // so we don't have to worry about going out of bounds
            int width = lines.get(i).length() + 6;
            if (i == 0) {
                addThreeEmptyLines(width, tokenizedLines);
            }

            var tokenizedLine = new ArrayList<String>();
            tokenizedLine.add("");
            tokenizedLine.add("");
            tokenizedLine.add("");
            for (var token : lines.get(i).toCharArray()) {
                tokenizedLine.add(String.valueOf(token));
            }
            tokenizedLine.add("");
            tokenizedLine.add("");
            tokenizedLine.add("");

            tokenizedLines.add(tokenizedLine);

            if (i == lines.size() - 1) {
                addThreeEmptyLines(width, tokenizedLines);
            }
        }
        return tokenizedLines;
    }

    private static void addThreeEmptyLines(int width, ArrayList<List<String>> tokenizedLines) {
        List<String> blankLine = IntStream.rangeClosed(0, width).mapToObj(x -> "").toList();
        tokenizedLines.add(blankLine);
        tokenizedLines.add(blankLine);
        tokenizedLines.add(blankLine);
    }
}
