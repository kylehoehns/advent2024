package com.kylehoehns.advent2024.day08;

import com.kylehoehns.advent2024.Puzzle;
import com.kylehoehns.advent2024.PuzzleInputReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Day08 implements Puzzle {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day08.class);
    private static final String DAY = "day08";

    private final PuzzleInputReader puzzleInputReader;

    public Day08(PuzzleInputReader puzzleInputReader) {
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
        List<String> input = puzzleInputReader.read(DAY);
        Set<String> antinodes = calculateAntinodes(input);
        return antinodes.size();
    }

    long getPart2() {
        List<String> input = puzzleInputReader.read(DAY);
        Set<String> antinodes = calculateAntinodesPart2(input);
        return antinodes.size();
    }

    record Antenna(int row, int column, Character ch) {
    }

    private Set<String> calculateAntinodes(List<String> input) {
        Set<Antenna> antennas = parseAntennas(input);
        Set<String> antinodes = new HashSet<>();

        for (var antenna : antennas) {
            for (var otherAntenna : antennas) {
                if (antenna.ch == otherAntenna.ch && !antenna.equals(otherAntenna)) {
                    int colDiff = otherAntenna.column - antenna.column;
                    int rowDiff = otherAntenna.row - antenna.row;

                    int antinodeX1 = antenna.column - colDiff;
                    int antinodeY1 = antenna.row - rowDiff;
                    int antinodeX2 = otherAntenna.column + colDiff;
                    int antinodeY2 = otherAntenna.row + rowDiff;


                    if (isValidAntinode(antinodeY1, antinodeX1, input)) {
                        antinodes.add(antinodeX1 + "," + antinodeY1);
                    }
                    if (isValidAntinode(antinodeY2, antinodeX2, input)) {
                        antinodes.add(antinodeX2 + "," + antinodeY2);
                    }
                }
            }
        }

        return antinodes;
    }

    private Set<Antenna> parseAntennas(List<String> input) {
        Set<Antenna> antennas = new HashSet<>();
        for (int row = 0; row < input.size(); row++) {
            String line = input.get(row);
            for (int column = 0; column < line.length(); column++) {
                char ch = line.charAt(column);
                if (Character.isLetterOrDigit(ch)) {
                    antennas.add(new Antenna(row, column, ch));
                }
            }
        }
        return antennas;
    }

    private Set<String> calculateAntinodesPart2(List<String> input) {
        Set<Antenna> antennas = parseAntennas(input);

        Map<Character, List<Antenna>> freqMap = new HashMap<>();
        for (Antenna a : antennas) {
            freqMap.computeIfAbsent(a.ch(), k -> new ArrayList<>()).add(a);
        }

        Set<String> antinodes = new HashSet<>();

        for (var entry : freqMap.entrySet()) {
            List<Antenna> list = entry.getValue();
            if (list.size() < 2) {
                continue;
            }

            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    Antenna a = list.get(i);
                    Antenna b = list.get(j);
                    int colDiff = b.column - a.column;
                    int rowDiff = b.row - a.row;

                    int gcd = gcd(colDiff, rowDiff);
                    int dxu = colDiff / gcd;
                    int dyu = rowDiff / gcd;

                    addLinePoints(a.row, a.column, dxu, dyu, input, antinodes);
                    addLinePoints(a.row, a.column, -dxu, -dyu, input, antinodes);

                    antinodes.add(b.column + "," + b.row);
                }
            }
        }

        return antinodes;
    }

    private void addLinePoints(int startRow, int startColumn, int dxu, int dyu, List<String> input, Set<String> antinodes) {
        int row = startRow;
        int column = startColumn;
        if (isValidAntinode(row, column, input)) {
            antinodes.add(column + "," + row);
        }

        while (true) {
            row += dyu;
            column += dxu;
            if (!isValidAntinode(row, column, input)) {
                break;
            }
            antinodes.add(column + "," + row);
        }
    }

    private boolean isValidAntinode(int row, int column, List<String> input) {
        return row >= 0 && row < input.size() && column >= 0 && column < input.get(row).length();
    }

    private int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a == 0 ? 1 : a;
    }

}
