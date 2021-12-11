package org.rt.advent.twentyone.day10;


import org.rt.advent.twentyone.day9.CaveMap;
import org.rt.advent.twentyone.util.DayPuzzle;
import org.rt.advent.twentyone.util.PuzzleFailedException;

import java.util.Collection;

public class Day10 extends DayPuzzle {
    @Override
    public String puzzle1() throws PuzzleFailedException {
        try {
            LineSyntaxChercker checker = new LineSyntaxChercker();
            return ""+checker.analyseLinesAndGetScore(getDayStreamAllLines());
        } catch (Exception e) {
            throw new PuzzleFailedException("no result for puzzle "+Day10.class.getSimpleName(),e);
        }
    }

    @Override
    public String puzzle2() throws PuzzleFailedException {
        try {

            LineSyntaxChercker checker = new LineSyntaxChercker();
            return ""+checker.analyseLinesAndGetCloseScore(getDayStreamAllLines());

        } catch (Exception e) {
            throw new PuzzleFailedException("no result for puzzle "+Day10.class.getSimpleName(),e);
        }
    }

    public static void main(String[] args) {
        Day10 day = new Day10();
        runPuzzles(day);
    }
}
