package com.kylehoehns.advent2024.day05;

import com.kylehoehns.advent2024.Puzzle;
import com.kylehoehns.advent2024.PuzzleInputReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class Day05 implements Puzzle {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day05.class);
    private static final String DAY = "day05";

    private final PuzzleInputReader puzzleInputReader;

    public Day05(PuzzleInputReader puzzleInputReader) {
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

    private record ParsedInput(PageOrderingRules pageOrderingRules, List<Update> updates) {
    }

    private static class PageOrderingRules {
        private final Map<Integer, Set<Integer>> orderings = new HashMap<>();

        PageOrderingRules(List<PageOrderingRule> pageOrderingRules) {
            pageOrderingRules.forEach(rule ->
                    orderings.computeIfAbsent(rule.firstPage(), k -> new HashSet<>())
                            .add(rule.secondPage())
            );
        }

        boolean isOrdered(int page1, int page2) {
            return orderings.getOrDefault(page1, Collections.emptySet()).contains(page2);
        }
    }

    private record PageOrderingRule(Integer firstPage, Integer secondPage) {
    }

    private record Page(Integer number, PageOrderingRules pageOrderingRules) implements Comparable<Page> {

        @Override
        public int compareTo(Page other) {
            if (pageOrderingRules.isOrdered(number, other.number)) {
                return -1;
            }
            if (pageOrderingRules.isOrdered(other.number, number)) {
                return 1;
            }
            return 0;
        }
    }

    private record Update(List<Page> pages) {

        Integer getMiddleNumber() {
            return pages.get(pages.size() / 2).number();
        }

        boolean isOrdered() {
            List<Page> sorted = pages.stream().sorted().toList();
            return pages.equals(sorted);
        }
    }

    int getPart1() {
        ParsedInput input = parseInput();
        return input.updates().stream()
                .filter(Update::isOrdered)
                .mapToInt(Update::getMiddleNumber)
                .sum();
    }

    int getPart2() {
        ParsedInput input = parseInput();

        return input.updates().stream()
                .filter(update -> !update.isOrdered())
                .map(update -> new Update(update.pages().stream().sorted().toList()))
                .mapToInt(Update::getMiddleNumber)
                .sum();
    }

    private ParsedInput parseInput() {
        List<String> inputLines = puzzleInputReader.read(DAY);

        List<PageOrderingRule> orderingRules = new ArrayList<>();
        List<Update> updates = new ArrayList<>();
        boolean parsingUpdates = false;

        for (String line : inputLines) {
            if (line.isEmpty()) {
                parsingUpdates = true;
                continue;
            }

            if (!parsingUpdates) {
                String[] parts = line.split("\\|");
                int firstPage = Integer.parseInt(parts[0]);
                int secondPage = Integer.parseInt(parts[1]);
                orderingRules.add(new PageOrderingRule(firstPage, secondPage));
            } else {
                List<Page> pages = Arrays.stream(line.split(","))
                        .map(Integer::parseInt)
                        .map(pageNumber -> new Page(pageNumber, new PageOrderingRules(orderingRules)))
                        .toList();
                updates.add(new Update(pages));
            }
        }

        return new ParsedInput(new PageOrderingRules(orderingRules), updates);
    }
}
