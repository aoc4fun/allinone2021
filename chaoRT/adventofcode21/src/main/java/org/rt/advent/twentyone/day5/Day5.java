package org.rt.advent.twentyone.day5;

import org.rt.advent.twentyone.util.DayPuzzle;
import org.rt.advent.twentyone.util.PuzzleFailedException;

import java.io.IOException;


public class Day5 extends DayPuzzle {
    @Override
    public String puzzle1() throws PuzzleFailedException {
        try {
            VentMap map = VentMap.VentMapFactory.createFromReader(getDayStream(), Line::isStrait);

            return ""+map.countPointWhereTwoPointsOverlaps();
        } catch (IOException e) {
            throw new PuzzleFailedException("no result for puzzle 1",e);
        }
    }

    @Override
    public String puzzle2() throws PuzzleFailedException {
        try {
            VentMap map = VentMap.VentMapFactory.createFromReader(getDayStream(), line -> true);

            return ""+map.countPointWhereTwoPointsOverlaps();
        } catch (IOException e) {
            throw new PuzzleFailedException("no result for puzzle 1",e);
        }
    }

    public static void main(String[] args) {
        Day5 day = new Day5();
        runPuzzles(day);
    }
}
