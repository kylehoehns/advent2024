package com.kylehoehns.advent2024.day03;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Day03Test {

    @Autowired
    private Day03 day;

    @MockitoBean
    private CommandLineRunner commandLineRunner;

    @Test
    void testPart1() {
        assertThat(day.getPart1()).isEqualTo(161);
    }

    @Test
    void testPart2() {
        assertThat(day.getPart2()).isEqualTo(48);
    }

}