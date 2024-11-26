package com.kylehoehns.advent2024;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PuzzleRunner {

    private final List<Puzzle> puzzles;

    public PuzzleRunner(List<Puzzle> puzzles) {
        this.puzzles = puzzles;
    }

    void run(String day) {
        var puzzle = puzzles.stream()
                .filter(p -> p.getDay().equals(day))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No puzzle found for day " + day));

        puzzle.run();
    }

}
