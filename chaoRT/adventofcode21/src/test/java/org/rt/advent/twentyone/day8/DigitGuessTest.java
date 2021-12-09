package org.rt.advent.twentyone.day8;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DigitGuessTest {

    @Test
    public void should_match_sample_two() {
        String[] testSignals = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab".split(" ");
        String[] toTransform  = "cdfeb fcadb cdfeb cdbaf".split(" ");
        DigitGuess guesser = new DigitGuess();
        guesser.guessSignals(testSignals);
        assertEquals(5, guesser.getSignalValue("cdfeb"));
        assertEquals(3, guesser.getSignalValue("fcadb"));
        assertEquals(5353, guesser.getSignalValue(Arrays.asList(toTransform)));
    }

}