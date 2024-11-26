package com.kylehoehns.advent2024;

import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class PuzzleInputReader {

    private final ResourceLoader resourceLoader;

    public PuzzleInputReader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<String> read(String day) {
        try {
            var resource = ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
                    .getResource("classpath:/puzzle-inputs/" + day + ".txt");

            var in = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
            try (var reader = new BufferedReader(in)) {
                return reader.lines().toList();
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to read from file", e);
        }
    }

    public List<Integer> readInts(String day) {
        return read(day).stream()
                .map(Integer::valueOf)
                .toList();
    }
}
