package com.kylehoehns.advent2024.day01;

import com.kylehoehns.advent2024.day00.Day00;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Day01Test {

    @Autowired
    private Day01 day;

    @MockitoBean
    private CommandLineRunner commandLineRunner;

    @Test
    void testPart1() {
        assertThat(day.getPart1()).isEqualTo(11);
    }

    @Test
    void testPart2() {
        assertThat(day.getPart2()).isEqualTo(31);
    }

}