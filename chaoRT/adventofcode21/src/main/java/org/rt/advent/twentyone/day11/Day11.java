package org.rt.advent.twentyone.day11;


import org.rt.advent.twentyone.util.DayPuzzle;
import org.rt.advent.twentyone.util.PuzzleFailedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class Day11 extends DayPuzzle {
    @Override
    public String puzzle1() throws PuzzleFailedException {
        try {

            return ""+getPuzzleSolver().flashAndCount(100);
        } catch (Exception e) {
            throw new PuzzleFailedException("no result for puzzle "+ Day11.class.getSimpleName(),e);
        }
    }
    protected OctopusMap  getPuzzleSolver() throws IOException {
        return OctopusMap.createFromLevels(getDayStream());
    }

    @Override
    public String puzzle2() throws PuzzleFailedException {
        try {

            return ""+getPuzzleSolver().getNextFullFlash();

        } catch (Exception e) {
            throw new PuzzleFailedException("no result for puzzle "+ Day11.class.getSimpleName(),e);
        }
    }

    public static void main(String[] args) {
        Day11 day = new Day11();
        runPuzzles(day);
    }
}
