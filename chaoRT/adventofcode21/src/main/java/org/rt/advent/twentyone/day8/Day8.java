package org.rt.advent.twentyone.day8;

import org.rt.advent.twentyone.day7.CrabsBandInSubmarine;
import org.rt.advent.twentyone.util.DayPuzzle;
import org.rt.advent.twentyone.util.PuzzleFailedException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day8 extends DayPuzzle {
    @Override
    public String puzzle1() throws PuzzleFailedException {
        try {
            DigitDisplayCounter counter = new DigitDisplayCounter();

            return ""+counter.countOccurrencesOfIdentifiableNumbers(getDayStream());
        } catch (Exception e) {
            throw new PuzzleFailedException("no result for puzzle 1",e);
        }
    }
    public Collection<String> filterEmptyValues(String[] values) {
        return Arrays.asList(Arrays.stream(values).filter(Predicate.not(String::isEmpty)).toArray(String[]::new));
    }

    @Override
    public String puzzle2() throws PuzzleFailedException {
        try {

            return ""+Files.lines(Path.of(getDayInput())).mapToLong(line -> {
                DigitGuess guesser = new DigitGuess();
                String[] parts = line.split("\\|");
                guesser.guessSignals(parts[0].split(" "));
                return (long)guesser.getSignalValue(filterEmptyValues(parts[1].split(" ")));
            }).sum();

        } catch (Exception e) {
            throw new PuzzleFailedException("no result for puzzle 1",e);
        }
    }

    public static void main(String[] args) {
        Day8 day = new Day8();
        runPuzzles(day);
    }
}
