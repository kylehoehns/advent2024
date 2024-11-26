package com.kylehoehns.advent2024;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Advent2024Application implements CommandLineRunner {

	private final PuzzleRunner puzzleRunner;

	public Advent2024Application(PuzzleRunner puzzleRunner) {
		this.puzzleRunner = puzzleRunner;
	}

	public static void main(String[] args) {
		SpringApplication.run(Advent2024Application.class, args);
	}

	@Override
	public void run(String... args) {
		String day = args[0];
		puzzleRunner.run(day);
	}
}
