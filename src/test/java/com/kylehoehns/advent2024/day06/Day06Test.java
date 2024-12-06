package com.kylehoehns.advent2024.day06;

import com.kylehoehns.advent2024.day05.Day05;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Day06Test {

    @Autowired
    private Day06 day;

    @MockitoBean
    private CommandLineRunner commandLineRunner;

    @Test
    void testPart1() {
        assertThat(day.getPart1()).isEqualTo(41);
    }

    @Test
    void testPart2() {
        assertThat(day.getPart2()).isEqualTo(6);
    }

}