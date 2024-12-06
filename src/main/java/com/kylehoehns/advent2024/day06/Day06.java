package com.kylehoehns.advent2024.day06;

import com.kylehoehns.advent2024.Puzzle;
import com.kylehoehns.advent2024.PuzzleInputReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Day06 implements Puzzle {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day06.class);
    private static final String DAY = "day06";
    private static final String UP = "^";
    private static final String DOWN = "v";
    private static final String LEFT = "<";
    private static final String RIGHT = ">";
    private static final String OBSTRUCTION = "#";

    private final PuzzleInputReader puzzleInputReader;

    public Day06(PuzzleInputReader puzzleInputReader) {
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

    record Grid(List<List<String>> data, Guard guard) {
    }

    record Position(int row, int col) {
    }

    record Guard(String direction, Position position) {
        public Position getNextPosition() {
            return switch (direction) {
                case UP -> new Position(position.row - 1, position.col);
                case DOWN -> new Position(position.row + 1, position.col);
                case LEFT -> new Position(position.row, position.col - 1);
                case RIGHT -> new Position(position.row, position.col + 1);
                default -> throw new IllegalStateException("Unexpected direction: " + direction);
            };
        }

        public Guard turnRight() {
            return switch (direction) {
                case UP -> new Guard(RIGHT, position);
                case RIGHT -> new Guard(DOWN, position);
                case DOWN -> new Guard(LEFT, position);
                case LEFT -> new Guard(UP, position);
                default -> throw new IllegalStateException("Unexpected direction: " + direction);
            };
        }
    }
    int getPart1() {
        var grid = parseGrid();
        return getVisitedPositions(grid).size();
    }

    private Set<Position> getVisitedPositions(Grid grid) {
        Set<Position> visitedPositions = new HashSet<>();
        // always add the original guard position
        visitedPositions.add(grid.guard.position);

        while (grid.guard != null) {
            grid = moveGuard(grid);
            if (grid.guard != null) {
                visitedPositions.add(grid.guard.position);
            }
        }

        return visitedPositions;
    }

    private Grid moveGuard(Grid grid) {
        Guard guard = grid.guard;

        while (true) {
            var nextPosition = guard.getNextPosition();
            if (isOutOfBounds(grid.data, nextPosition)) {
                return new Grid(grid.data, null);
            }

            if (!grid.data.get(nextPosition.row).get(nextPosition.col).equals(OBSTRUCTION)) {
                return new Grid(grid.data, new Guard(guard.direction, nextPosition));
            }

            guard = guard.turnRight();
        }
    }

    private boolean isOutOfBounds(List<List<String>> data, Position position) {
        return position.row < 0 || position.row >= data.size() ||
                position.col < 0 || position.col >= data.get(position.row).size();
    }

    private Grid parseGrid() {
        var lines = puzzleInputReader.read(DAY);
        List<List<String>> data = new ArrayList<>();
        Position guardPosition = null;

        for (int row = 0; row < lines.size(); row++) {
            List<String> rowData = new ArrayList<>();
            for (int col = 0; col < lines.get(row).length(); col++) {
                String token = String.valueOf(lines.get(row).charAt(col));
                rowData.add(token);
                if (token.equals(UP)) {
                    guardPosition = new Position(row, col);
                }
            }
            data.add(rowData);
        }

        return new Grid(data, new Guard(UP, guardPosition));
    }

    int getPart2() {
        int loopsDetected = 0;
        Grid originalGrid = parseGrid();
        Set<Position> visitedPositions = getVisitedPositions(originalGrid);

        for (var position : visitedPositions) {
            Grid grid = parseGrid();
            grid.data.get(position.row).set(position.col, OBSTRUCTION);
            if (hasLoop(grid)) {
                loopsDetected++;
            }
        }

        return loopsDetected;
    }

    private boolean hasLoop(Grid grid) {
        Set<Guard> seenGuards = new HashSet<>();
        seenGuards.add(grid.guard);

        while (grid.guard != null) {
            grid = moveGuard(grid);
            if (grid.guard != null && !seenGuards.add(grid.guard)) {
                return true;
            }
        }

        return false;
    }
}
